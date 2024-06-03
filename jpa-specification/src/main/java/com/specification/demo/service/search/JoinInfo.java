package com.specification.demo.service.search;

import javax.persistence.criteria.JoinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinInfo {
  private String key;
  @Builder.Default private JoinType joinType = JoinType.INNER;
}
