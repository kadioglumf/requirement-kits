package com.specification.demo.error;

public enum ErrorType {


    UNKNOWN_REAL_ESTATE_SERVICE_ERROR(5001, "Unknown real estate service exception", 500),
    FILTER_PARAMETER_BLACKLIST_ERROR(5002, "Blacklist filter parameter", 400),
    FILTER_PARAMETER_CANNOT_NULL_ERROR(5003, "Filter parameter cannot be null.", 400),
    FILTER_TYPE_NOT_SUITABLE_ERROR(5004, "Cannot search with this filter type and industry type.", 400),
    SEARCH_UNKNOWN_ERROR(5005, "Unknown search error", 500),
    DATE_FORMAT_NOT_FOUND_ERROR(5006, "Date format not found.", 400),
    LOCAL_DATE_PARSE_ERROR(5007, "LocalDate parse exception.", 400),
    NUMBER_PARSE_ERROR(5008, "Number parse error", 500),
    CATEGORY_NOT_FOUND_ERROR(5009, "CategoryModel not found", 400),
    AVAILABLE_DATE_PAST_ERROR(5010, "Available date should not be older than today", 400),
    REQUEST_OBJECT_NOT_VALID_ERROR(5011, "Request object not valid.", 400),
    EMPTY_LIST_ERROR(5012, "List is EMPTY.", 400),
    PARAMETER_CANNOT_BE_NULL_ERROR(5013, "Parameter cannot be null", 400),
    PARAMETER_NOT_VALID_ERROR(5014, "Parameter cannot be null", 400),
    ADVERTISEMENT_NOT_FOUND_ERROR(5015, "advertisement is not exist", 400),
    PROPERTY_NOT_FOUND_ERROR(5016, "property not found", 400),
    DUPLICATE_PROPERTY_KEY_ERROR(5017, "duplicate property key error", 400),
    FORBIDDEN_ERROR(5018, "You cannot reach this resource!", 403),
    MAXIMUM_NUMBER_OF_FILES_ERROR(5019, "You have reached the maximum number of files error!", 400),
    MULTIPART_FILES_EMPTY_ERROR(5020, "Multipart files cannot be empty error", 400),
    IMAGE_RESIZE_ERROR(5021, "Image resize error!", 400),
    FILE_NOT_FOUND_ERROR(5022, "File not found!", 400),
    FILE_MISSING_ERROR(5023, "Missing file error!", 400),


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
