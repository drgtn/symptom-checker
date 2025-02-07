package com.healthily.repository;

import com.healthily.model.Symptom;

import java.util.List;
import java.util.Optional;

public interface SymptomRepository {
    List<Symptom> findAll();

    Optional<Symptom> findById(String dataId);
}
