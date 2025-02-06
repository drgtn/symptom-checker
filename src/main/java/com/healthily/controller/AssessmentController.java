package com.healthily.controller;

import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;
import com.healthily.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessment")
@RequiredArgsConstructor
public class AssessmentController {
    private final AssessmentService assessmentService;

    @PostMapping("/start")
    public ResponseEntity<AssessmentResponseDto> startAssessment(@RequestBody AssessmentRequestDto request) {
        return ResponseEntity.ok(assessmentService.startAssessment(request));
    }

    @PostMapping("/{assessmentId}/answer")
    public ResponseEntity<AssessmentResponseDto> answerQuestion(
            @PathVariable String assessmentId,
            @RequestBody AssessmentAnswerRequestDto request) {
        return ResponseEntity.ok(assessmentService.answerQuestion(assessmentId, request));
    }

    @GetMapping("/{assessmentId}/result")
    public ResultResponseDto result(@PathVariable String assessmentId) {
        return assessmentService.getResult(assessmentId);
    }
}
