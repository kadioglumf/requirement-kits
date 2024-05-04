package com.kadioglumf.kafka2.handler;

import com.kadioglumf.kafka2.model.BaseEvent;
import com.kadioglumf.kafka2.producer.KafkaProducer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Component
@ConditionalOnProperty(name = "kafka.enable-producer", havingValue = "true")
@Log4j2
public class EventHandler {

    @Autowired
    private KafkaProducer<String, BaseEvent> kafkaProducer;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void send(BaseEvent event) {
        String id = UUID.randomUUID().toString();
        log.info("kafka event publishing with id: {}", id);
        kafkaProducer.send(event.getTopic(), id, event);
    }
}
