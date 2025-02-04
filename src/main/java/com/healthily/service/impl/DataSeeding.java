package com.healthily.service.impl;

import com.healthily.model.Condition;
import com.healthily.model.Symptom;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeding {
    private final DynamoDbTable<Symptom> symptomTable;
    private final DynamoDbTable<Condition> conditionTable;

    public void seedConditions() {
        List<String[]> conditionRows = loadCsv("data/conditions_data.csv");
        if (conditionRows.isEmpty()) return;
        log.info("Seeding conditions...");
        for (String[] row : conditionRows.subList(1, conditionRows.size())) {
            conditionTable.putItem(Condition.builder().conditionId(row[1]).prevalence(Double.parseDouble(row[2])).build());
        }
    }

    public void seedSymptoms() {
        List<String[]> symptomRows = loadCsv("data/symptoms_data.csv");
        if (symptomRows.isEmpty()) return;

        log.info("Seeding symptoms...");
        String[] headers = symptomRows.get(0);

        for (String[] row : symptomRows.subList(1, symptomRows.size())) {
            Map<String, Double> conditionProbabilities = new HashMap<>();
            for (int i = 2; i < row.length; i++) {
                conditionProbabilities.put(headers[i], Double.parseDouble(row[i]));
            }
            symptomTable.putItem(Symptom.builder().symptomId(row[1]).conditionProbabilities(conditionProbabilities).build());
        }
    }

    private static List<String[]> loadCsv(String filePath) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(filePath).getInputStream()))) {
            return reader.readAll();
        } catch (Exception e) {
            log.error("Error reading CSV file: {}", filePath, e);
            return Collections.emptyList();
        }
    }
}
