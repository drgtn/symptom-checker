package com.healthily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Assessment {
    public static final String ASSESSMENT = "ASSESSMENT";
    private String assessmentId;
    private String userId;
    private List<String> reportedSymptoms;
    private Map<String, Double> conditionProbabilities;
    private Map<String, Boolean> askedQuestions = new HashMap<>();

    @DynamoDbPartitionKey
    public String getAssessmentId() {
        return assessmentId;
    }

}
