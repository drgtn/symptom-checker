package com.healthily;

import com.healthily.service.impl.DataDefinitionService;
import com.healthily.service.impl.DataSeeding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SymptomCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SymptomCheckerApplication.class, args);
    }

    @Bean
    @ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
    public CommandLineRunner run(DataDefinitionService dataDefinitionService, DataSeeding dataSeeding) {

        return args -> {
            log.info("Starting data seeding...");
            dataDefinitionService.createConditionTable();
            dataDefinitionService.createSymptomTable();
            dataDefinitionService.createUserTable();
            dataSeeding.seedConditions();
            dataSeeding.seedSymptoms();

            log.info("Data seeding completed!");

        };
    }

}
