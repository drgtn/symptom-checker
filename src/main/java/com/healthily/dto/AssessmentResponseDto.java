package com.healthily.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssessmentResponseDto {
    private String assessmentId;
    private String nextQuestionId;
}
