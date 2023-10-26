package com.specification.demo.error;

public enum ErrorType {


    UNKNOWN_JPA_SPECIFICATION_SERVICE_ERROR(5001, "Unknown jpa specification service exception", 500),
    FILTER_PARAMETER_BLACKLIST_ERROR(5002, "Blacklist filter parameter", 400),
    FILTER_PARAMETER_CANNOT_NULL_ERROR(5003, "Filter parameter cannot be null.", 400),
    FILTER_TYPE_NOT_SUITABLE_ERROR(5004, "Cannot search with this filter type and industry type.", 400),
    SEARCH_UNKNOWN_ERROR(5005, "Unknown search error", 500),
    DATE_FORMAT_NOT_FOUND_ERROR(5006, "Date format not found.", 400),
    LOCAL_DATE_PARSE_ERROR(5007, "LocalDate parse exception.", 400),
    NUMBER_PARSE_ERROR(5008, "Number parse error", 500),
    FORBIDDEN_ERROR(5009, "You cannot reach this resource!", 403),


    //External service error types

    ;


    private final int code;
    private final String description;
    private final int httpStatusCode;

    ErrorType(int code, String description, int httpStatusCode) {
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}
