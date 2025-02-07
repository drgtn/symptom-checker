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
import static com.healthily.model.User.USER_ID_INDEX;

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
        boolean tableExists = dynamoDbClient.listTables().tableNames().contains(USER);
        if (!tableExists) {
            CreateTableRequest userTable = CreateTableRequest.builder()
                    .tableName(USER)
                    .keySchema(KeySchemaElement.builder()
                            .attributeName("email")
                            .keyType(KeyType.HASH)
                            .build())
                    .attributeDefinitions(AttributeDefinition.builder()
                                    .attributeName("email")
                                    .attributeType(ScalarAttributeType.S)
                                    .build(),
                            AttributeDefinition.builder()
                                    .attributeName("userId")
                                    .attributeType(ScalarAttributeType.S)
                                    .build())
                    .globalSecondaryIndexes(GlobalSecondaryIndex.builder()
                            .indexName(USER_ID_INDEX)
                            .keySchema(KeySchemaElement.builder()
                                    .attributeName("userId")
                                    .keyType(KeyType.HASH)
                                    .build())
                            .projection(Projection.builder()
                                    .projectionType(ProjectionType.ALL)
                                    .build())
                            .build())
                    .billingMode(BillingMode.PAY_PER_REQUEST)
                    .build();
            dynamoDbClient.createTable(userTable);
        }
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
