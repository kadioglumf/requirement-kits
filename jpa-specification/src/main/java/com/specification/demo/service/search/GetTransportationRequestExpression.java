package com.specification.demo.service.search;

import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.payload.response.ErrorType;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTransportationRequestExpression extends BaseSearchExpression {

  private static class SingletonHelper {
    private static final GetTransportationRequestExpression INSTANCE =
        new GetTransportationRequestExpression();
  }

  public static GetTransportationRequestExpression getInstance() {
    return GetTransportationRequestExpression.SingletonHelper.INSTANCE;
  }

  private final HashMap<RoleType, List<String>> WHITE_LIST_PARAMETERS =
      new HashMap<>() {
        {
          put(
              RoleType.ADMIN_OR_MODERATOR,
              ExpressionKeyConstants.getTransportationAdminOrModKeys());
          put(RoleType.USER, ExpressionKeyConstants.getTransportationPartnerKeys());
        }
      };

  @Override
  public <T> Expression<?> getExpression(Root<T> root, String key, RoleType searchRoleType) {
    checkSearchKey(key, WHITE_LIST_PARAMETERS.get(searchRoleType));
    LinkedList<JoinInfo> joinInfos = new LinkedList<>();
    switch (key) {
      case ExpressionKeyConstants.TRANSPORTATION_REQUEST_ID:
      case ExpressionKeyConstants.CREATION_DATE:
      case ExpressionKeyConstants.LAST_UPDATE_DATE:
      case ExpressionKeyConstants.NUMBER_OF_SALES:
      case ExpressionKeyConstants.IS_APPROVED:
        return root.get(key);
      case ExpressionKeyConstants.USER_ID:
        joinInfos.add(JoinInfo.builder().key(ExpressionKeyConstants.ORDERS).build());
        return findJoinByKeys(root, joinInfos).get(key);
      case ExpressionKeyConstants.ORDERS:
        joinInfos.add(
            JoinInfo.builder().key(ExpressionKeyConstants.ORDERS).joinType(JoinType.LEFT).build());
        return findJoinByKeys(root, joinInfos).get(ExpressionKeyConstants.ID);
      default:
        throw new CustomServiceException(
            ErrorType.FILTER_PARAMETER_BLACKLIST_ERROR, "You cannot search with: " + key);
    }
  }
}
