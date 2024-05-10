package com.specification.demo.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.specification.demo.service.search.FieldType;
import com.specification.demo.service.search.FilterType;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ConditionRequest implements Serializable {

  @NotBlank private String key;
  @NotNull private FieldType fieldType;
  @NotNull private FilterType filterType;
  private transient Object value;
  private transient Object valueTo;
  private transient List<Object> values;
}
