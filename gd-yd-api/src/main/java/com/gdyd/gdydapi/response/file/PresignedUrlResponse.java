package com.gdyd.gdydapi.response.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Pre-signed URL 응답")
public record PresignedUrlResponse(
        @Schema(description = "Pre-signed URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/.../...")
        String presignedUrl
) {
    public static PresignedUrlResponse of(String presignedUrl) {
        return PresignedUrlResponse.builder()
                .presignedUrl(presignedUrl)
                .build();
    }
}
