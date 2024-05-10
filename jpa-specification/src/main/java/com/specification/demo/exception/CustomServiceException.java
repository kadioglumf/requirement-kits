package com.specification.demo.exception;

import com.specification.demo.payload.response.BaseErrorResponse;
import com.specification.demo.payload.response.ErrorType;

public class CustomServiceException extends RuntimeException {

  private final BaseErrorResponse errorResponse;

  public CustomServiceException(BaseErrorResponse errorResponse) {
    this.errorResponse = errorResponse;
  }

  public CustomServiceException(ErrorType errorType) {
    this.errorResponse = new BaseErrorResponse(errorType);
  }

  public CustomServiceException(int statusCode, ErrorType errorType) {
    this.errorResponse = new BaseErrorResponse(statusCode, errorType);
  }

  public CustomServiceException(int statusCode, ErrorType errorType, String errorMessage) {
    this.errorResponse = new BaseErrorResponse(statusCode, errorType, errorMessage);
  }

  public CustomServiceException(ErrorType errorType, String errorMessage) {
    this.errorResponse = new BaseErrorResponse(errorType, errorMessage);
  }

  public BaseErrorResponse getErrorResponse() {
    return errorResponse;
  }
}
