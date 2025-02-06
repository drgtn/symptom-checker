package com.healthily.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import static com.healthily.model.Assessment.ASSESSMENT;
import static com.healthily.model.Condition.CONDITION;
import static com.healthily.model.Symptom.SYMPTOM;
import static com.healthily.model.User.USER;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataDefinitionService {
    private final DynamoDbClient dynamoDbClient;

    public void createSymptomTable() {
        createTable(SYMPTOM, "dataId");
    }

    public void createConditionTable() {
        createTable(CONDITION, "dataId");

    }

    public void createUserTable() {
        createTable(USER, "email");

    }

    public void createAssessmentTable() {
        createTable(ASSESSMENT, "assessmentId");

    }

    private void createTable(String tableName, String partitionKey) {
        boolean tableExists = dynamoDbClient.listTables().tableNames().contains(tableName);
        if (!tableExists) {
            CreateTableRequest request = CreateTableRequest.builder()
                    .tableName(tableName)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName(partitionKey)
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                            .attributeName(partitionKey)
                            .attributeType(ScalarAttributeType.S)
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();

            dynamoDbClient.createTable(request);
            log.info("{} table created!", tableName);
        }
    }
}
