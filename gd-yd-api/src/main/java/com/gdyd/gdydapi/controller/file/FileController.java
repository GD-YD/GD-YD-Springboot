package com.gdyd.gdydapi.controller.file;

import com.gdyd.gdydapi.response.file.PresignedUrlResponse;
import com.gdyd.gdydapi.service.file.FileQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "File", description = "File 처리 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileController {

    private final FileQueryService fileQueryService;

    @Operation(summary = "S3 파일 업로드 presigned URL 생성 API",
            description = "S3 파일 업로드 presigned URL을 생성하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "S3 파일 업로드 presigned URL 생성 성공")
    })
    @Parameters(value = {
            @Parameter(name = "prefix", description = "파일 경로 prefix", required = true, example = "identification/university"),
            @Parameter(name = "fileName", description = "파일 이름과 확장자", required = true, example = "test.jpg")
    })
    @GetMapping("/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(
            @RequestParam("prefix") String prefix,
            @RequestParam("fileName") String fileName
    ) {
        PresignedUrlResponse response = fileQueryService.getPresignedUrl(prefix, fileName);
        return ResponseEntity.ok(response);
    }
}
