from fastapi import FastAPI
from utils import create_spark_session, get_avg_loan_data

app = FastAPI()

# test 경로에서 HDFS 파일 내용을 출력하는 라우트
@app.get("/api/v1/avg_loan")
async def get_avg_loan():
    # Spark 세션 생성
    spark = create_spark_session()
    try:
        # 대출 이율 평균을 계산하는 함수 호출
        data = get_avg_loan_data(spark)

        if( spark ):
            spark.stop()
            
        return data
    except Exception as e:
        return {"error": str(e)}
