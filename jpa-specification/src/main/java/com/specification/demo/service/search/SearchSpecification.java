package com.specification.demo.service.search;

import com.specification.demo.error.ErrorType;
import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.payload.search.ConditionRequest;
import com.specification.demo.payload.search.FilterRequest;
import com.specification.demo.payload.search.SearchRequest;
import com.specification.demo.payload.search.SortRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -9153865343320750644L;

    private final SearchRequest request;
    private final SearchExpressionType searchExpressionType;
    private final SearchRoleType searchRoleType;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {
        try {
            Predicate predicate = null;

            for (FilterRequest filter : this.request.getFilters()) {
                for (ConditionRequest condition : filter.getConditions()) {
                    log.info("Filter: {} {} {}", filter.getKey(), condition.getFilterType().toString(), condition.getValue());
                    predicate = condition.getFilterType().build(root, cb, filter, predicate, condition, searchExpressionType, searchRoleType);
                    if (predicate == null) {
                        throw new CustomServiceException(ErrorType.FILTER_TYPE_NOT_SUITABLE_ERROR);
                    }
                }
            }

            List<Order> orders = new ArrayList<>();
            for (SortRequest sort : this.request.getSorts()) {
                orders.add(sort.getDirection().build(root, cb, sort, searchExpressionType, searchRoleType));
            }

            query.orderBy(orders);
            return predicate;
        } catch (CustomServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new CustomServiceException(ErrorType.SEARCH_UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 100));
    }

}

