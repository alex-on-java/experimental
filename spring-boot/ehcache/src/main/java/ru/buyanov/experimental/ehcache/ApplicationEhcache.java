package ru.buyanov.experimental.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author A.Buyanov 06.02.2016
 */
@SpringBootApplication
@EnableCaching
public class ApplicationEhcache {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEhcache.class, args);
    }
}
