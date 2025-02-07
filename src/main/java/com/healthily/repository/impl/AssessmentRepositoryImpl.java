package com.healthily.repository.impl;

import com.healthily.model.Assessment;
import com.healthily.repository.AssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AssessmentRepositoryImpl implements AssessmentRepository {
    private final DynamoDbTable<Assessment> assessmentTable;

    @Override
    public void save(Assessment assessment) {
        assessmentTable.putItem(assessment);
    }

    @Override
    public void update(Assessment assessment) {
        assessmentTable.updateItem(assessment);
    }

    @Override
    public void remove(String assessment) {
        findById(assessment).ifPresent(assessmentTable::deleteItem);
    }

    @Override
    public Optional<Assessment> findById(String assessmentId) {
        return Optional.ofNullable(assessmentTable.getItem(r -> r.key(k -> k.partitionValue(assessmentId))));
    }
}
