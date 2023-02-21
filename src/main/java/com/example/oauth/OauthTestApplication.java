package com.example.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Entity;

@EnableJpaAuditing
@SpringBootApplication
public class OauthTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthTestApplication.class, args);
    }

}
