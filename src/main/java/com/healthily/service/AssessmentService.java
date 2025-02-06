package com.healthily.service;

import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;

public interface AssessmentService {
    AssessmentResponseDto startAssessment(AssessmentRequestDto request);

    AssessmentResponseDto answerQuestion(String assessmentId, AssessmentAnswerRequestDto answerRequestDto);

    ResultResponseDto getResult(String assessmentId);
}
