package com.kafka.producer;

import com.kafka.producer.service.KafkaProducerService;
import com.kafka.producer.service.model.TestEventModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class KafkaProducerApplication {

    private final KafkaProducerService kafkaProducerService;

    public KafkaProducerApplication(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void run() {
        kafkaProducerService.testEvent(new TestEventModel("test message from kafka producer"));
    }
}
