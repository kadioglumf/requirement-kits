package com.specification.demo.service;

import com.specification.demo.model.TransportationRequestModel;
import com.specification.demo.payload.request.ConditionRequest;
import com.specification.demo.payload.request.Operator;
import com.specification.demo.payload.request.SearchRequest;
import com.specification.demo.repository.TransportationRepository;
import com.specification.demo.service.search.ExpressionKeyConstants;
import com.specification.demo.service.search.FieldType;
import com.specification.demo.service.search.FilterType;
import com.specification.demo.service.search.RoleType;
import com.specification.demo.service.search.SearchSpecification;
import com.specification.demo.service.search.TransportationRequestExpression;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class TransportationRequestService {

  private final TransportationRepository transportationRepository;
  private final TransportationRequestExpression transportationRequestExpression;

  @EventListener(ApplicationReadyEvent.class)
  public Page<TransportationRequestModel> getPartnerTransportationMarketList() {
    Long userId = 16934L;

    SearchRequest request =
        SearchRequest.builder()
            .filters(new ArrayList<>())
            .sorts(new ArrayList<>())
            .page(0)
            .size(100)
            .build();

    ConditionRequest test =
        ConditionRequest.builder()
            .key(ExpressionKeyConstants.TRANSPORTATION_REQUEST_ID)
            .fieldType(FieldType.STRING)
            .filterType(FilterType.EQUAL)
            .value("TR811171")
            .operator(Operator.AND)
            .build();
    request.addFilterByParameter(List.of(test), Operator.OR);

    ConditionRequest isApprovedCondition =
        ConditionRequest.builder()
            .key(ExpressionKeyConstants.IS_APPROVED)
            .fieldType(FieldType.BOOLEAN)
            .filterType(FilterType.EQUAL)
            .value(false)
            .operator(Operator.AND)
            .build();
    request.addFilterByParameter(Collections.singletonList(isApprovedCondition), Operator.AND);

    ConditionRequest numberOfSalesCondition =
        ConditionRequest.builder()
            .key(ExpressionKeyConstants.NUMBER_OF_SALES)
            .fieldType(FieldType.NUMBER)
            .filterType(FilterType.IN)
            .values(List.of(1, 2, 3, 4))
            .operator(Operator.AND)
            .build();
    request.addFilterByParameter(Collections.singletonList(numberOfSalesCondition), Operator.OR);

    ConditionRequest orders =
        ConditionRequest.builder()
            .key(ExpressionKeyConstants.ORDERS)
            .filterType(FilterType.IS_NULL)
            .operator(Operator.OR)
            .build();

    ConditionRequest notOrderCondition =
        ConditionRequest.builder()
            .key(ExpressionKeyConstants.USER_ID)
            .fieldType(FieldType.NUMBER)
            .filterType(FilterType.NOT_EQUAL)
            .value(userId)
            .operator(Operator.OR)
            .build();
    request.addFilterByParameter(List.of(orders, notOrderCondition), Operator.AND);

    var list = searchTransportations(request, RoleType.USER);
    return list;
  }

  private Page<TransportationRequestModel> searchTransportations(
      SearchRequest request, RoleType roleType) {
    SearchSpecification<TransportationRequestModel> specification =
        new SearchSpecification<>(request, transportationRequestExpression, roleType);
    Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    return transportationRepository.findAll(specification, pageable);
  }
}
