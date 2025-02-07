package com.healthily.repository.impl;

import com.healthily.model.Symptom;
import com.healthily.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class SymptomRepositoryImpl implements SymptomRepository {
    private final DynamoDbTable<Symptom> symptomTable;

    @Override
    public List<Symptom> findAll() {
        return symptomTable.scan().items().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Symptom> findById(String dataId) {
        return Optional.ofNullable(symptomTable.getItem(r -> r.key(k -> k.partitionValue(dataId))));
    }
}
