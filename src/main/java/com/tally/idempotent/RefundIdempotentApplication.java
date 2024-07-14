package com.tally.idempotent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RefundIdempotentApplication {

    public static void main(String[] args) {
        SpringApplication.run(RefundIdempotentApplication.class, args);
    }

}
