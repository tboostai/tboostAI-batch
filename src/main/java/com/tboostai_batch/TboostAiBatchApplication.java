package com.tboostai_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TboostAiBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TboostAiBatchApplication.class, args);
    }

}
