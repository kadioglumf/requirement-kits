package com.kadioglumf.kafka2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Locale;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BaseEvent implements Serializable {
    private String className;
    private String topic;
    private Locale locale;
}
