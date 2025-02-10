package com.healthily.fixtures;

public class AssessmentAnswerRequestDto {
    private AssessmentAnswerRequestDto() {
    }

    public static com.healthily.dto.AssessmentAnswerRequestDto aAssessmentAnswerRequestDto() {
        return com.healthily.dto.AssessmentAnswerRequestDto
                .builder()
                .questionId("Watery or itchy eyes")
                .response("yes")
                .build();
    }
}
