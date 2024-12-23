package org.example;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableFeignClients
@EnableRabbit
@EnableWebSecurity
public class NoteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoteServiceApplication.class, args);

    }
}
