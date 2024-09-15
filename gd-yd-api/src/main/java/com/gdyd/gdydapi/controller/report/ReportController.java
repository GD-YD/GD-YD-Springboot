package com.gdyd.gdydapi.controller.report;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.service.report.ReportCommandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Report", description = "Report 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportCommandService reportCommandService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody String request) {
        reportCommandService.discordTest(request);
        return ResponseEntity.ok().build();
    }
}
