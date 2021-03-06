package com.lambdaschool.apollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ApolloApplication {

    public static void main(String[] args) {
            SpringApplication.run(ApolloApplication.class,
                    args);
    }
}