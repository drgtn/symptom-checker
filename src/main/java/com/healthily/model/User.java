package com.healthily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String USER = "USER";
    public static final String USER_ID_INDEX = "userId_index";
    private String userId;
    private String email;
    private String password;
    private int age;
    private Gender gender;

    @DynamoDbPartitionKey
    public String getEmail() {
        return email;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = USER_ID_INDEX)
    public String getUserId() {
        return userId;
    }
}
