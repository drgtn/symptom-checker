package com.healthily.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssessmentAnswerRequestDto {
    String questionId;
    String response;
}
