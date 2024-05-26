package com.gdyd.gdydinfra.aws.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private static final int EXPIRATION_TIME_MILLIS_INDEX = 1000 * 60;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    /**
     * Get presigned URL for S3 file upload
     * @param prefix directory name
     * @param fileName origin file name
     * @return presigned URL (PUT method)
     */
    public String getPresignedUrl(String prefix, String fileName) {
        String generatedFileName = generateFileName(prefix, fileName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = generatePresignedUrlRequest(generatedFileName);
        URL presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        return presignedUrl.toString();
    }

    /**
     * Generate file name with prefix and origin file name
     * @param prefix directory name
     * @param fileName origin file name
     * @return generated file name -> test/2b6f0b4f-1b7d-...-0b1f1b0f1b0f-test.jpg
     */
    public String generateFileName(String prefix, String fileName) {
        final String uuid = UUID.randomUUID().toString();
        return prefix + "/" + uuid + "-" + fileName;
    }

    /**
     * Generate presigned URL request(PUT method)
     * @param fileName file name
     * @return presigned URL request
     */
    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(generateExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );
        return generatePresignedUrlRequest;
    }

    /**
     * Generate expiration time for presigned URL
     * @return expiration time - example: 2021-07-01T00:00:00.000Z
     */
    private Date generateExpiration() {
        Date expirationTime = new Date();
        long expirationInMillis = expirationTime.getTime() + EXPIRATION_TIME_MILLIS_INDEX;
        expirationTime.setTime(expirationInMillis);
        return expirationTime;
    }
}
