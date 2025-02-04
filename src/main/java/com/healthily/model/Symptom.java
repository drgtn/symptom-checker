package com.healthily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Map;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Symptom {
    public static final String SYMPTOM = "SYMPTOM";
    private String symptomId;
    private Map<String, Double> conditionProbabilities;

    @DynamoDbPartitionKey
    public String getSymptomId() {
        return symptomId;
    }
}
