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
        String key = Paths.get("family-profiles", file.getOriginalFilename()).toString();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return key;
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