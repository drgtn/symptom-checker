package com.healthily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Symptom {
    public static final String SYMPTOM = "SYMPTOM";
    private String dataId;
    private Map<String, Double> conditionProbabilities;

    @DynamoDbPartitionKey
    public String getDataId() {
        return dataId;
    }
}
