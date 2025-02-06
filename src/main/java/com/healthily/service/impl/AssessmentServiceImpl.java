package com.healthily.service.impl;

import com.healthily.dto.AssessmentAnswerRequestDto;
import com.healthily.dto.AssessmentRequestDto;
import com.healthily.dto.AssessmentResponseDto;
import com.healthily.dto.ResultResponseDto;
import com.healthily.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    @Override
    public AssessmentResponseDto startAssessment(AssessmentRequestDto request) {
        return null;
    }

    @Override
    public AssessmentResponseDto answerQuestion(String assessmentId, AssessmentAnswerRequestDto answerRequestDto) {
        return null;
    }

    @Override
    public ResultResponseDto getResult(String assessmentId) {
        return null;
    }
}
