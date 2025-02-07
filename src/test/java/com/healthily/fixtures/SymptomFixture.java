package com.healthily.fixtures;

import com.healthily.model.Symptom;

import java.util.Map;

public class SymptomFixture {
    private SymptomFixture() {
    }

    public static Symptom aWateryOrItchyEyes() {
        return Symptom.builder()
                .dataId("Watery or itchy eyes")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.95,
                        "COVID-19", 0.05,
                        "Common Cold", 0.1
                ))
                .build();
    }

    public static Symptom aCough() {
        return Symptom.builder()
                .dataId("Cough")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.1,
                        "COVID-19", 0.7,
                        "Common Cold", 0.6
                ))
                .build();
    }

    public static Symptom aFever() {
        return Symptom.builder()
                .dataId("Fever")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.0,
                        "COVID-19", 0.85,
                        "Common Cold", 0.1
                ))
                .build();
    }

    public static Symptom aLossOfSmellOrTaste() {
        return Symptom.builder()
                .dataId("Loss of smell or taste")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.05,
                        "COVID-19", 0.8,
                        "Common Cold", 0.05
                ))
                .build();
    }

    public static Symptom aFatigue() {
        return Symptom.builder()
                .dataId("Fatigue")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.2,
                        "COVID-19", 0.75,
                        "Common Cold", 0.3
                ))
                .build();
    }

    public static Symptom aShortnessOfBreath() {
        return Symptom.builder()
                .dataId("Shortness of breath")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.05,
                        "COVID-19", 0.5,
                        "Common Cold", 0.05
                ))
                .build();
    }

    public static Symptom aRunnyNose() {
        return Symptom.builder()
                .dataId("Runny nose")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.85,
                        "COVID-19", 0.2,
                        "Common Cold", 0.8
                ))
                .build();
    }

    public static Symptom aHeadache() {
        return Symptom.builder()
                .dataId("Headache")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.3,
                        "COVID-19", 0.6,
                        "Common Cold", 0.4
                ))
                .build();
    }

    public static Symptom aSneezing() {
        return Symptom.builder()
                .dataId("Sneezing")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.9,
                        "COVID-19", 0.1,
                        "Common Cold", 0.7
                ))
                .build();
    }

    public static Symptom aNasalCongestion() {
        return Symptom.builder()
                .dataId("Nasal congestion")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.75,
                        "COVID-19", 0.4,
                        "Common Cold", 0.85
                ))
                .build();
    }

    public static Symptom aSoreThroat() {
        return Symptom.builder()
                .dataId("Sore throat")
                .conditionProbabilities(Map.of(
                        "Hayfever", 0.05,
                        "COVID-19", 0.65,
                        "Common Cold", 0.75
                ))
                .build();
    }

}
