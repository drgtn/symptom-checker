package com.healthily.fixtures;

import com.healthily.dto.ResultResponseDto;

import java.util.Map;

public class ResultResponseDtoFixture {
    private ResultResponseDtoFixture() {
    }

    public static ResultResponseDto aCommonCold() {
        return ResultResponseDto.builder()
                .condition("Common Cold")
                .probabilities(Map.of("COVID-19", 0.006603234496458019,
                        "Hayfever", 0.0791027583016626,
                        "Common Cold", 0.9142940072018794))
                .build();
    }

    public static ResultResponseDto aHayfever() {
        return ResultResponseDto.builder()
                .condition("Hayfever")
                .probabilities(Map.of("COVID-19", 0.00011097466805032709,
                        "Hayfever", 0.9850924029252395,
                        "Common Cold", 0.014796622406710274))
                .build();
    }

    public static ResultResponseDto aCovid19() {
        return ResultResponseDto.builder()
                .condition("COVID-19")
                .probabilities(Map.of("COVID-19", 0.6601941747572816,
                        "Hayfever", 0.0,
                        "Common Cold", 0.33980582524271844))
                .build();
    }
}
