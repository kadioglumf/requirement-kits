package com.specification.demo.service.search;

import com.specification.demo.payload.request.Operator;
import javax.persistence.criteria.Predicate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PredicateCondition {
  private Predicate predicate;
  private Operator operator;
}
