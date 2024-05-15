package com.specification.demo.service.search;

import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.payload.response.ErrorType;
import com.specification.demo.utils.PropertyAccessorUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.util.CollectionUtils;

public abstract class BaseSearchExpression {

  public abstract <T> Expression<?> getExpression(
      Root<T> root, String key, RoleType searchRoleType);

  protected void checkSearchKey(String key, List<String> whiteListKeys) {
    if (CollectionUtils.isEmpty(whiteListKeys)) {
      throw new CustomServiceException(ErrorType.FORBIDDEN_ERROR);
    }
    if (!whiteListKeys.contains(key)) {
      throw new CustomServiceException(
          ErrorType.FILTER_PARAMETER_BLACKLIST_ERROR, "You cannot search with: " + key);
    }
  }

  protected <T> Join<T, ?> getJoin(Root<T> root, String key, JoinType joinType) {
    return root.getJoins().stream()
        .filter(
            c ->
                PropertyAccessorUtils.valueOrDefault(() -> c.getAttribute().getName(), "")
                    .equals(key))
        .findFirst()
        .orElse(root.join(key, joinType));
  }

  protected Join<?, ?> findJoinByKeys(Root<?> root, LinkedList<JoinInfo> joinInfos) {
    Join<?, ?> currentJoin = null;
    Join<?, ?> lastJoin = null;

    for (JoinInfo joinInfo : joinInfos) {
      if (currentJoin == null) {

        currentJoin = findJoinByKey(root.getJoins(), joinInfo);
      } else {
        currentJoin = findJoinByKey(currentJoin.getJoins(), joinInfo);
      }

      if (currentJoin == null) {
        currentJoin =
            Objects.requireNonNullElse(lastJoin, root)
                .join(joinInfo.getKey(), joinInfo.getJoinType());
      }
      lastJoin = currentJoin;
    }

    return currentJoin;
  }

  private <T> Join<T, ?> findJoinByKey(Set<Join<T, ?>> joins, JoinInfo joinInfo) {
    return joins.stream()
        .filter(
            c ->
                PropertyAccessorUtils.valueOrDefault(() -> c.getAttribute().getName(), "")
                    .equals(joinInfo.getKey()))
        .findFirst()
        .orElse(null);
  }
}
