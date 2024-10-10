import os
from pyspark.sql import SparkSession
from pyspark.ml.clustering import KMeans, KMeansModel
from pyspark.ml.feature import VectorAssembler
from pyspark.sql import functions as F
from pyspark.sql.window import Window

class KMeansService:
    def __init__(self):
        # SparkSession을 수동으로 생성하여 SparkContext 초기화
        self.spark = None  # 초기에는 Spark 세션을 만들지 않음
        self.model_path = "./kmeans_model"
        self.assembler = VectorAssembler(inputCols=["deposit_ratio", "savings_ratio", "stock_ratio"], outputCol="features")

    # Spark 세션 생성 (중지된 세션 복구)
    def get_spark_session(self):
        if not self.spark or self.spark._jsc is None:
            self.spark = SparkSession.builder \
                .appName("FastAPI") \
                .getOrCreate()
        return self.spark


    # HDFS에서 데이터를 읽어와 K-Means 학습
    def train_kmeans_model_from_hdfs(self, k: int = 3) -> str:
        spark = None
        try:
            # Spark 세션 생성
            spark = self.get_spark_session()

            # HDFS에서 테이블 데이터 로드
            deposit_df = spark.read.parquet("hdfs://oracle1.mypjt.xyz:9000/user/ubuntu/deposits")
            savings_df = spark.read.parquet("hdfs://oracle1.mypjt.xyz:9000/user/ubuntu/savings")
            stock_df = spark.read.parquet("hdfs://oracle1.mypjt.xyz:9000/user/ubuntu/stock_users")

            # 사용자별 예금, 적금, 주식 총액 계산
            total_assets_df = deposit_df.groupBy("user_id").agg(
                F.sum("deposit_money").alias("total_deposit")
            ).join(
                savings_df.groupBy("user_id").agg(F.sum("balance").alias("total_savings")), "user_id", "outer"
            ).join(
                stock_df.groupBy("user_id").agg(F.sum(F.col("amount") * F.col("buy_average")).alias("total_stock")), "user_id", "outer"
            ).na.fill(0)

            # 자산 비율 계산
            asset_ratio_df = total_assets_df.withColumn(
                "total_assets", F.col("total_deposit") + F.col("total_savings") + F.col("total_stock")
            ).withColumn(
                "deposit_ratio", F.col("total_deposit") / F.col("total_assets")
            ).withColumn(
                "savings_ratio", F.col("total_savings") / F.col("total_assets")
            ).withColumn(
                "stock_ratio", F.col("total_stock") / F.col("total_assets")
            )

            # 피처 벡터 생성
            asset_features_df = self.assembler.transform(asset_ratio_df)

            # K-Means 클러스터링
            kmeans = KMeans(k=3, seed=1)
            model = kmeans.fit(asset_features_df)

            # 모델 저장
            model.write().overwrite().save(self.model_path)

            # 클러스터 정보를 데이터프레임에 추가
            cluster_df = model.transform(asset_features_df).select("user_id", "prediction")

            # 각 클러스터에서 가장 많이 사용된 예금 및 적금 상품을 집계
            self.most_frequent_deposit = deposit_df.join(cluster_df, "user_id") \
                .groupBy("prediction", "deposit_product_id") \
                .count() \
                .withColumn("rank", F.row_number().over(Window.partitionBy("prediction").orderBy(F.desc("count")))) \
                .filter(F.col("rank") == 1) \
                .select("prediction", "deposit_product_id")

            self.most_frequent_savings = savings_df.join(cluster_df, "user_id") \
                .groupBy("prediction", "savings_product_id") \
                .count() \
                .withColumn("rank", F.row_number().over(Window.partitionBy("prediction").orderBy(F.desc("count")))) \
                .filter(F.col("rank") == 1) \
                .select("prediction", "savings_product_id")

            return "K-Means 모델 학습 및 추천 데이터 준비 완료"
        except Exception as e:
            return f"모델 학습 중 오류 발생: {str(e)}"

    # 새로운 사용자 클러스터링
    def predict_cluster(self, deposit_ratio: float, savings_ratio: float, stock_ratio: float) -> int:
        spark = None
        try:
            # Spark 세션 생성
            spark = self.get_spark_session()

            # 모델 로드 (모델 경로 확인 및 예외 처리)
            if not os.path.exists(self.model_path):
                raise RuntimeError(f"모델 경로가 존재하지 않습니다: {self.model_path}")
            
            # 모델 로드
            model = KMeansModel.load(self.model_path)
            new_user_data = [(deposit_ratio, savings_ratio, stock_ratio)]
            new_user_df = spark.createDataFrame(new_user_data, ['deposit_ratio', 'savings_ratio', 'stock_ratio'])

            # 벡터화
            new_user_features = self.assembler.transform(new_user_df)

            # 예측
            prediction = model.transform(new_user_features).select("prediction").collect()[0][0]
            return prediction
        except Exception as e:
            raise RuntimeError(f"예측 중 오류 발생: {str(e)}")

    # 클러스터에 따른 추천 제공
    def recommend_products(self, cluster_id: int) -> dict:
        spark = None
        try:
            # Spark 세션 생성
            spark = self.get_spark_session()

            if self.most_frequent_deposit is None or self.most_frequent_savings is None:
                raise RuntimeError("모델 학습이 완료되지 않아 추천할 데이터를 찾을 수 없습니다.")

            deposit_recommendation = self.most_frequent_deposit.filter(self.most_frequent_deposit.prediction == cluster_id).select("deposit_product_id").collect()[0][0]
            savings_recommendation = self.most_frequent_savings.filter(self.most_frequent_savings.prediction == cluster_id).select("savings_product_id").collect()[0][0]

            return {
                "deposit_recommendation": deposit_recommendation,
                "savings_recommendation": savings_recommendation
            }
        except Exception as e:
            raise RuntimeError(f"추천 서비스 중 오류 발생: {str(e)}")