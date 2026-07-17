package com.golfettozh.sonata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("dev")
public class SonataApplication {

    static void main(String[] args) {
        SpringApplication.run(SonataApplication.class, args);
    }

}
