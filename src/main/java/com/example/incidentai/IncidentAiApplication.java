package com.example.incidentai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IncidentAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(IncidentAiApplication.class, args);
    }
}
