package com.healthily.fixtures;

import com.healthily.model.Condition;

public class ConditionFixture {
    private ConditionFixture() {
    }

    public static Condition aCommonCold() {
        return Condition.builder()
                .dataId("Common Cold")
                .prevalence(0.5)
                .build();
    }

    public static Condition aHayfever() {
        return Condition.builder()
                .dataId("Hayfever")
                .prevalence(0.3)
                .build();
    }

    public static Condition aCovid19() {
        return Condition.builder()
                .dataId("COVID-19")
                .prevalence(0.2)
                .build();
    }
}
