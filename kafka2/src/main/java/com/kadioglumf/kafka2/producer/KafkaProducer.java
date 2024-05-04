package com.kadioglumf.kafka2.producer;

import com.kadioglumf.core.utils.UserDeviceDetailsUtils;
import com.kadioglumf.kafka2.exception.KafkaProducerException;
import com.kadioglumf.kafka2.model.BaseEvent;
import com.kadioglumf.kafka2.util.ConvertUtils;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaProducer<K extends Serializable, V extends BaseEvent> {

    private final KafkaTemplate<K, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<K, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String topicName, K key, V message) {
        try {
            message.setClassName(message.getClass().getName());
            message.setLocale(UserDeviceDetailsUtils.getUserLocale());
            log.info("Sending message={} to topic={}", message, topicName);
            kafkaTemplate.send(topicName, key, ConvertUtils.convertObjectToJsonData(message));
        } catch (KafkaException e) {
            log.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message,
                    e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message: " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    public <T> BiConsumer<? super T, ? super Throwable> getKafkaCallback() {
        return (t, ex) -> {
            if (ex != null) {
                throw new RuntimeException("test ex");
            }
            else {
                log.info("success: {}", t);
            }
        };
    }
}
