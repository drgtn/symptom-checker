package com.healthily.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResultResponseDto {
    private String condition;
    private Map<String, Double> probabilities;
}
