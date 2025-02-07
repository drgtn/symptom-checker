package com.healthily.fixtures;

import com.healthily.dto.AssessmentRequestDto;

import java.util.List;

public class AssessmentRequestDtoFixture {
    private AssessmentRequestDtoFixture() {
    }

    public static AssessmentRequestDto aAssessmentRequestSneezingRunnyNose() {
        return AssessmentRequestDto.builder()
                .userId("userId")
                .initialSymptoms(List.of("Sneezing", "Runny nose"))
                .build();
    }

    public static AssessmentRequestDto aAssessmentRequestLossOfSmellOrTaste() {
        return AssessmentRequestDto.builder()
                .userId("userId")
                .initialSymptoms(List.of("Loss of smell or taste"))
                .build();
    }
}
