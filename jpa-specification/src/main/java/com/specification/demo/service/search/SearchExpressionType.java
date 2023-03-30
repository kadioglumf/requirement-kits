package com.specification.demo.service.search;

import com.specification.demo.error.ErrorType;
import com.specification.demo.exception.CustomServiceException;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;

public enum SearchExpressionType {

    YOUR_CATEGORY_NAME {

        private final HashMap<SearchRoleType, List<String>> YOUR_CATEGORY_NAME_WHITE_LIST_PARAMETERS = new HashMap<>() {{
            put(SearchRoleType.ADMIN, ExpressionKeyConstants.get_YourCategoryName_AdminKeys());
            put(SearchRoleType.PUBLIC, ExpressionKeyConstants.get_YourCategoryName_PublicKeys());
        }};

        @Override
        public <T> Expression<?> getExpression(Root<T> root, String key, SearchRoleType searchRoleType) {
            checkSearchKey(key, YOUR_CATEGORY_NAME_WHITE_LIST_PARAMETERS.get(searchRoleType));

            switch (key) {
                case ExpressionKeyConstants.ID:
                case ExpressionKeyConstants.CREATION_DATE:
                    return root.get(key);
                /*case ExpressionKeyConstants.STATUS:
                    return root.get(ExpressionKeyConstants.STATUS);
                case ExpressionKeyConstants.ADDRESS:
                    return root.join(ExpressionKeyConstants.CONTACT_INFORMATION).get(key);
                case ExpressionKeyConstants.PROPERTY_KEY:
                    return root.join(ExpressionKeyConstants.PROPERTIES).get(key);*/
                default:
                    throw new CustomServiceException(ErrorType.FILTER_PARAMETER_BLACKLIST_ERROR);
            }
        }
    },


    ;



    public abstract <T> Expression<?> getExpression(Root<T> root, String key, SearchRoleType searchRoleType);


    public void checkSearchKey(String key, List<String> whiteListKeys) {
        if (CollectionUtils.isEmpty(whiteListKeys)) {
            throw new CustomServiceException(ErrorType.FORBIDDEN_ERROR);
        }
        if (!whiteListKeys.contains(key)) {
            throw new CustomServiceException(ErrorType.FILTER_PARAMETER_BLACKLIST_ERROR);
        }
    }
}
