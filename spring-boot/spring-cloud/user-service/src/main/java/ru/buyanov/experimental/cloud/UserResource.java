package ru.buyanov.experimental.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author A.Buyanov 31.07.2016.
 *
 */
@RestController
public class UserResource {
    @Value("${experiment.val:none}")
    private String val;

    @PostConstruct
    public void init() {
        System.out.println("\n\n");
        System.out.println("--- " + val + " ---");
        System.out.println("\n\n");
    }
}
