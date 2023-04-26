package com.kafka.producer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.producer.service.KafkaProducerService;
import com.kafka.producer.service.model.TestEventModel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void testEvent(TestEventModel event) {
        try {
            String valueAsString = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("test-event", valueAsString);
        }
        catch (Exception e) {

        }
    }
}
