package com.kafka.consumer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.consumer.service.KafkaConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private static final Logger logger = Logger.getLogger(KafkaConsumerServiceImpl.class.getName());

    @Override
    @KafkaListener(topics = "test-event", groupId = "group-id")
    public void listenTestEvent(String message) {
        logger.info("test-event message : " + message);
    }
}
