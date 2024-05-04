package com.kadioglumf.kafka2.producer;

import com.kadioglumf.kafka2.model.BaseEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringEventProducer {

    private final ApplicationEventPublisher publisher;

    public void publish(String topic, BaseEvent event) {
        event.setTopic(topic);
        publisher.publishEvent(event);
    }
}
