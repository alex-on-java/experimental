package ru.buyanov.experimental.jpa.e2;

import ch.qos.logback.classic.Level;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author A.Buyanov 20.08.2016.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.buyanov.experimental.jpa.e2.repository")
public class ApplicationJpaExperimentTwo {
    public static void main(String[] args) {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger("org.hibernate.tool.hbm2ddl.SchemaExport")).setLevel(Level.WARN);
        SpringApplication.run(ApplicationJpaExperimentTwo.class, args);
    }
}
