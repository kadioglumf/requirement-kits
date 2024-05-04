package com.kadioglumf.kafka2.consumer;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<T extends Serializable> {
    void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
