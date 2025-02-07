package com.healthily.fixtures;

import com.healthily.dto.AssessmentRequestDto;
import com.healthily.model.Assessment;

import java.util.Map;

public class AssessmentFixture {
    private AssessmentFixture() {
    }

    public static Assessment aInitialAssessment(AssessmentRequestDto request) {
        return Assessment.builder()
                .userId(request.getUserId())
                .reportedSymptoms(request.getInitialSymptoms())
                .conditionProbabilities(Map.of("COVID-19", 0.007789678675754629,
                        "Hayfever", 0.44693281402142165,
                        "Common Cold", 0.5452775073028239))
                .build();
    }

}
