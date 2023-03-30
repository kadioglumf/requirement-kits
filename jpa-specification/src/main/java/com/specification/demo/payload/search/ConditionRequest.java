package com.specification.demo.payload.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specification.demo.service.search.FilterType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConditionRequest implements Serializable {

    @NotNull
    private FilterType filterType;
    private transient Object value;
    private transient Object valueTo;
    private transient List<Object> values;
}
