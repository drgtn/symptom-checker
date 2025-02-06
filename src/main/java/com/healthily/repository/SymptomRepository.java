package com.healthily.repository;

import com.healthily.model.Symptom;

import java.util.List;

public interface SymptomRepository {
    List<Symptom> findAll();
}
