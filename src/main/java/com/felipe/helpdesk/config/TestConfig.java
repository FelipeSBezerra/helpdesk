package com.felipe.helpdesk.config;

import com.felipe.helpdesk.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    DbService dbService;

    @Bean
    public void instanciaDb(){
        this.dbService.instanciaDb();
    }
}
