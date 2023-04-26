package com.kafka.producer;

import com.kafka.producer.service.KafkaProducerService;
import com.kafka.producer.service.model.TestEventModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

    private final KafkaProducerService kafkaProducerService;

    public KafkaProducerApplication(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        kafkaProducerService.testEvent(new TestEventModel("test message from kafka producer"));
    }
}
