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
public class Assessment {
    public static final String ASSESSMENT = "ASSESSMENT";
    private String assessmentId;
    private String userId;
    private int questionCount = 0;

    @DynamoDbPartitionKey
    public String getAssessmentId() {
        return assessmentId;
    }


}
