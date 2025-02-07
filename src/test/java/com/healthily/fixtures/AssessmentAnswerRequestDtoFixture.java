package com.healthily.fixtures;

import com.healthily.dto.AssessmentAnswerRequestDto;

public class AssessmentAnswerRequestDtoFixture {
    private AssessmentAnswerRequestDtoFixture() {
    }

    public static AssessmentAnswerRequestDto aAssessmentAnswerRequestDto(String nextQuestion, String response) {
        return AssessmentAnswerRequestDto.builder()
                .response(response)
                .questionId(nextQuestion)
                .build();
    }
}
