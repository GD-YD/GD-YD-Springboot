package com.gdyd.gdydapi.service.file;

import com.gdyd.gdydapi.response.file.PresignedUrlResponse;
import com.gdyd.gdydinfra.aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileQueryService {
    private final S3Service s3Service;

    public PresignedUrlResponse getPresignedUrl(String prefix, String fileName) {
        String presignedUrl = s3Service.getPresignedUrl(prefix, fileName);
        return PresignedUrlResponse.of(presignedUrl);
    }
}
