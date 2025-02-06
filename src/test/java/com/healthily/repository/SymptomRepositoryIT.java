package com.healthily.repository;

import com.healthily.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.healthily.fixtures.SymptomFixture.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SymptomRepositoryIT extends BaseIT {
    @Autowired
    private SymptomRepository symptomRepository;

    @Test
    public void testFindAll() {
        assertThat(symptomRepository.findAll()).containsExactly(
                aWateryOrItchyEyes(),
                aCough(),
                aFever(),
                aLossOfSmellOrTaste(),
                aFatigue(),
                aShortnessOfBreath(),
                aRunnyNose(),
                aHeadache(),
                aSneezing(),
                aNasalCongestion(),
                aSoreThroat());


    }

}
