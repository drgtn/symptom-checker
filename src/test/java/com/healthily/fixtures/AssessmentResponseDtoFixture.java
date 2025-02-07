package com.healthily.fixtures;

import com.healthily.dto.AssessmentResponseDto;

import static com.healthily.fixtures.SymptomFixture.aWateryOrItchyEyes;

public class AssessmentResponseDtoFixture {
    private AssessmentResponseDtoFixture() {
    }

    public static AssessmentResponseDto aWateryOrItchyEyesAssessmentResponseDto(AssessmentResponseDto responseOne) {
        return AssessmentResponseDto.builder()
                .assessmentId(responseOne.getAssessmentId())
                .nextQuestionId(aWateryOrItchyEyes().getDataId())
                .build();
    }
}
