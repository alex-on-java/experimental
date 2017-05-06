package ru.buyanov.experimental.jpa.e1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author A.Buyanov 13.02.2016
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.buyanov.experimental.jpa.e1.repository")
public class ApplicationJpaExperimentOne {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationJpaExperimentOne.class, args);
    }
}
