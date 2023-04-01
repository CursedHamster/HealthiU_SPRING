package com.example.healthiu;

import com.example.healthiu.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

@SpringBootApplication
public class HealthiUApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthiUApplication.class, args);
    }

}
