package com.specification.demo.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SearchRequest implements Serializable {

  private static final long serialVersionUID = 8514625832019794838L;

  private List<FilterRequest> filters;

  private List<SortRequest> sorts;

  @NotNull private Integer page;

  @NotNull private Integer size;

  public List<FilterRequest> getFilters() {
    if (Objects.isNull(this.filters)) return new ArrayList<>();
    return this.filters;
  }

  public List<SortRequest> getSorts() {
    if (Objects.isNull(this.sorts)) return new ArrayList<>();
    return this.sorts;
  }

  public void addFilterByParameter(
      @NonNull List<ConditionRequest> conditionRequestList, @Nullable Operator operator) {
    FilterRequest filter = new FilterRequest();

    filter.setConditions(conditionRequestList);
    filter.setOperator(operator);

    if (Objects.isNull(this.filters)) {
      this.filters = new ArrayList<>();
    }
    filters.add(filter);
  }
}
