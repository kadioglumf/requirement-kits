package com.specification.demo.service.search;

import com.specification.demo.payload.search.SortRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public enum SortDirection {

    ASC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request, SearchExpressionType searchExpressionType, SearchRoleType searchRoleType) {
            return cb.asc(searchExpressionType.getExpression(root, request.getKey(), searchRoleType));
        }
    },
    DESC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request, SearchExpressionType searchExpressionType, SearchRoleType searchRoleType) {
            return cb.desc(searchExpressionType.getExpression(root, request.getKey(), searchRoleType));
        }
    };

    public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequest request, SearchExpressionType searchExpressionType, SearchRoleType searchRoleType);

}

