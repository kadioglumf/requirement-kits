package com.kadioglumf.kafka2.consumer;

import com.kadioglumf.kafka2.model.BaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
@ConditionalOnProperty(name = "kafka.enable-consumer", havingValue = "true")
public class KafkaConsumerImpl implements KafkaConsumer<String> {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    @KafkaListener(id = "${kafka.consumer-config.consumer-group-id}", topics = "${kafka.consumer-config.consumer-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(jsonDta -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                JsonNode jsonNode = objectMapper.readValue(jsonDta, JsonNode.class);
                BaseEvent e = (BaseEvent) objectMapper.readValue(jsonDta, Class.forName(jsonNode.get("className").asText()));
                eventPublisher.publishEvent(e);

            } catch (JsonProcessingException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            log.info("kafka received message: {}", jsonDta);
        });

    }
}
