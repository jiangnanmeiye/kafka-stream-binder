package com.springcloudstream.kafka_bind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class KafkaBindApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaBindApplication.class, args);
    }

    @Bean
    public Function<String, String> toUpperCase() {
        return value -> {
            System.out.println("Going run!");
            return value.toUpperCase();
        };
    }
}
