package com.healthily.config;

import com.healthily.model.Condition;
import com.healthily.model.Symptom;
import com.healthily.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

import static com.healthily.model.Condition.CONDITION;
import static com.healthily.model.Symptom.SYMPTOM;
import static com.healthily.model.User.USER;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbClient dynamoDbClient(@Value("${aws.access-key}") String accessKey,
                                         @Value("${aws.secret-key}") String secretKey,
                                         @Value("${aws.dynamodb.endpoint}") String endpoint) {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                )).build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient) {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public DynamoDbTable<Symptom> symptomTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(SYMPTOM, TableSchema.fromBean(Symptom.class));
    }

    @Bean
    public DynamoDbTable<Condition> conditionTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(CONDITION, TableSchema.fromBean(Condition.class));
    }

    @Bean
    public DynamoDbTable<User> userTable(DynamoDbEnhancedClient enhancedClient) {
        return enhancedClient.table(USER, TableSchema.fromBean(User.class));
    }
}
