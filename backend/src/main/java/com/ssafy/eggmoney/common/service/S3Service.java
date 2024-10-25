package com.ssafy.eggmoney.common.service;

import com.ssafy.eggmoney.common.config.S3Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;


@Service
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(S3Client s3Client, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }
    public String uploadFile(MultipartFile file) throws IOException{
        // S3 경로를 명시적으로 설정
        String key = "family/" + file.getOriginalFilename();

        // 파일의 Content-Type 설정
        String contentType = file.getContentType();
        if (contentType == null) {
            contentType = "application/octet-stream";  // 기본 Content-Type 설정
        }

        // S3에 파일 업로드 요청
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)  // Content-Type 설정
                .contentDisposition("inline")  // Content-Disposition을 inline으로 설정
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        // S3 파일 URL 반환
        String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + key;
        return fileUrl;  // 업로드된 파일의 S3 URL 반환
    }
    public InputStream downloadFiles(String key){
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3Client.getObject(getObjectRequest);
    }

}
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
//                S3Client.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
//            @Value("${cloud.aws.credentials.accessKey}") String accessKey,
//            @Value("${cloud.aws.credentials.secretKey}") String secretKey,
//            @Value("${cloud.aws.region.static}") String region,
//            @Value("${cloud.aws.region.static}") String bucketName
//public void uploadFile(String fileName, String filePath){
//    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//            .bucket(bucketName)
//            .key(fileName)
//            .build();
//
//    s3Client.putObject(putObjectRequest, Paths.get(filePath));
//}
//
//public void downloadFile(String fileName, String destinationPath){
//    GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//            .bucket(bucketName)
//            .key(fileName)
//            .build();
//
//    s3Client.getObject(getObjectRequest, Paths.get(destinationPath));
//}