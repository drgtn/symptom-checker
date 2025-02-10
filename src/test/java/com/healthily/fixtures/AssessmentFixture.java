package com.healthily.fixtures;

import com.healthily.dto.AssessmentRequestDto;
import com.healthily.model.Assessment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class AssessmentFixture {

    private AssessmentFixture() {
    }

    public static Assessment aInitialAssessment(AssessmentRequestDto request, DecimalFormat decimalFormat) {
        HashMap<String, Double> conditionProbabilities = new HashMap<>() {{
            put("COVID-19", 0.007789678675754629);
            put("Hayfever", 0.44693281402142165);
            put("Common Cold", 0.5452775073028239);
        }};
        return Assessment.builder()
                .userId(request.getUserId())
                .reportedSymptoms(request.getInitialSymptoms())
                .conditionProbabilities(ofNullable(decimalFormat)
                        .map(dm -> getRoundedProbabilities(conditionProbabilities, dm))
                        .orElse(conditionProbabilities))
                .askedQuestions(new HashMap<>())
                .build();
    }

    public static Assessment aWateryOrIchyEyesAssessment(AssessmentRequestDto request) {
        return Assessment.builder()
                .userId(request.getUserId())
                .reportedSymptoms(request.getInitialSymptoms())
                .conditionProbabilities(new HashMap<>() {{
                    put("COVID-19", 0.0008122652045892989);
                    put("Hayfever", 0.885470606152909);
                    put("Common Cold", 0.1137171286425018);
                }})
                .askedQuestions(new HashMap<>() {{
                    put("Watery or itchy eyes", true);
                }})
                .build();
    }

    private static Map<String, Double> getRoundedProbabilities(HashMap<String, Double> conditionProbabilities,
                                                               DecimalFormat decimalFormat) {
        return conditionProbabilities.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> Double.valueOf(decimalFormat.format(entry.getValue()))
                ));
    }
}
