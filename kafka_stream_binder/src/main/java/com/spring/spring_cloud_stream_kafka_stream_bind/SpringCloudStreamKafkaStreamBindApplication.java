package com.spring.spring_cloud_stream_kafka_stream_bind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@SpringBootApplication
public class SpringCloudStreamKafkaStreamBindApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamKafkaStreamBindApplication.class, args);
    }

    public static class WordCountProcessorApplication {
        static final int WINDOW_SIZE_SECONDS = 30;

        @Bean
        public Function<KStream<Object, String>, KStream<Object, WordCount>> process() {
            return input -> input
                    .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                    .map(((key, value) -> new KeyValue<>(value, value)))
                    .groupByKey()
                    .windowedBy(TimeWindows.of(Duration.ofSeconds(WINDOW_SIZE_SECONDS)))
                    .count()
                    .toStream()
                    .map(((key, value) -> new KeyValue<>(null, new WordCount(key.key(), value, new Date(key.window().start()), new Date(key.window().end())))));
        }
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class WordCount {
        private String word;
        private long count;
        private Date start;
        private Date end;
    }
}