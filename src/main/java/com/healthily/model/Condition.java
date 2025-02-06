package com.healthily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condition {
    public static final String CONDITION = "CONDITION";
    private String dataId;
    private double prevalence;

    @DynamoDbPartitionKey
    public String getDataId() {
        return dataId;
    }
}
