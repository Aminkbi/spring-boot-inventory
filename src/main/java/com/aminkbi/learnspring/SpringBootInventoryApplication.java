package com.aminkbi.learnspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringBootInventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInventoryApplication.class, args);
    }

}
