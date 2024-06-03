package com.specification.demo.service.search;

import java.util.Arrays;
import java.util.List;

public class ExpressionKeyConstants {

  public static final String CREATION_DATE = "creationDate";
  public static final String LAST_UPDATE_DATE = "lastUpdateDate";
  public static final String USER_ID = "userId";
  public static final String ID = "id";
  public static final String TRANSPORTATION_REQUEST_ID = "transportationRequestId";
  public static final String NUMBER_OF_SALES = "numberOfSales";
  public static final String IS_APPROVED = "isApproved";
  public static final String ORDERS = "orders";

  public static List<String> getTransportationAdminOrModKeys() {
    return Arrays.asList(
        TRANSPORTATION_REQUEST_ID,
        CREATION_DATE,
        LAST_UPDATE_DATE,
        NUMBER_OF_SALES,
        IS_APPROVED,
        USER_ID,
        ORDERS);
  }

  public static List<String> getTransportationPartnerKeys() {
    return Arrays.asList(
        TRANSPORTATION_REQUEST_ID,
        CREATION_DATE,
        LAST_UPDATE_DATE,
        NUMBER_OF_SALES,
        IS_APPROVED,
        USER_ID,
        ORDERS);
  }
}
