package com.gdyd.gdydapi.response.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Pagination 형식 응답")
public record PageResponse<T>(
        @Schema(description = "총 페이지 수", example = "100")
        int totalItems,

        @Schema(description = "페이징 목록")
        List<T> contents
) {
    public static <T> PageResponse<T> of(List<T> contents) {
        return PageResponse.<T>builder()
                .totalItems(contents.size())
                .contents(contents)
                .build();
    }
}
