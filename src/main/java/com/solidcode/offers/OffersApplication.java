package com.solidcode.offers;

import com.solidcode.offers.util.DatabaseEnvironmentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class OffersApplication {

    @Autowired
    private DatabaseEnvironmentLogger databaseEnvironmentLogger;

    public static void main(String[] args) {

        SpringApplication.run(OffersApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> databaseEnvironmentLogger.logDatabaseEnvironmentVariables();
    }
}
