package com.healthily.fixtures;

import com.healthily.dto.AssessmentResponseDto;

import static com.healthily.fixtures.SymptomFixture.aSoreThroat;
import static com.healthily.fixtures.SymptomFixture.aWateryOrItchyEyes;

public class AssessmentResponseDtoFixture {
    private AssessmentResponseDtoFixture() {
    }

    public static AssessmentResponseDto aWateryOrItchyEyesAssessmentResponseDto(String assessmentId) {
        return AssessmentResponseDto.builder()
                .assessmentId(assessmentId)
                .nextQuestionId(aWateryOrItchyEyes().getDataId())
                .build();
    }

    public static AssessmentResponseDto aSoreThroatResponseDto(String assessmentId) {
        return AssessmentResponseDto.builder()
                .assessmentId(assessmentId)
                .nextQuestionId(aSoreThroat().getDataId())
                .build();
    }
}
