package com.healthily.repository;

import com.healthily.model.Assessment;

import java.util.Optional;

public interface AssessmentRepository {
    void save(Assessment assessment);

    Optional<Assessment> findById(String assessmentId);

    void update(Assessment assessment);

    void remove(String assessment);
}
