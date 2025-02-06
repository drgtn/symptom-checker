package com.healthily.repository;

import com.healthily.model.Condition;

import java.util.List;

public interface ConditionRepository {
    List<Condition> findAll();
}
