package com.kafka.producer.service;

import com.kafka.producer.service.model.TestEventModel;

public interface KafkaProducerService {

    void testEvent(TestEventModel event);
}
