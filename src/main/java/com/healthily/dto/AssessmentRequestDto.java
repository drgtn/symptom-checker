package com.healthily.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AssessmentRequestDto {
    private String userId;
    private List<String> initialSymptoms;
}
