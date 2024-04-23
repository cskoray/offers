package com.solidcode.offers.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseEnvironmentLogger {


    @Autowired
    private Environment environment;

    public void logDatabaseEnvironmentVariables() {
        System.out.println("=========Database Environment Variables=========");
        String dbUsername = environment.getProperty("DATASOURCE_USERNAME");
        String dbPassword = environment.getProperty("DATASOURCE_PASSWORD");

        System.out.println("Database Username-----------> " + dbUsername);
        System.out.println("Database Password-----------> " + dbPassword);
    }
}
