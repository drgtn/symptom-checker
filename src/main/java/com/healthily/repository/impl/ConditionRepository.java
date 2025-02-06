package com.healthily.repository.impl;

import com.healthily.model.Condition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;
import java.util.stream.Collectors;
@Repository
@RequiredArgsConstructor
public class ConditionRepository implements com.healthily.repository.ConditionRepository {
    private final DynamoDbTable<Condition> conditionTable;

    @Override
    public List<Condition> findAll() {
        return conditionTable.scan().items().stream().collect(Collectors.toList());
    }
}
