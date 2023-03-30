package com.specification.demo.service.search;

import com.specification.demo.error.ErrorType;
import com.specification.demo.exception.CustomServiceException;
import com.specification.demo.utils.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;

public enum FieldType {

    BOOLEAN {
        public Object parse(String value) {
            return Boolean.valueOf(value);
        }
    },

    CHAR {
        public Object parse(String value) {
            return value.charAt(0);
        }
    },

    DATE {
        public Object parse(String value) {
            return DateUtils.parseStringToObjectByFormat(value);
        }
    },

    NUMBER {
        public Object parse(String value) {
            try {
                return NumberUtils.createNumber(value);
            } catch (NumberFormatException ex) {
                throw new CustomServiceException(ErrorType.NUMBER_PARSE_ERROR, ex.getMessage());
            }
        }
    },

    STRING {
        public Object parse(String value) {
            return value;
        }
    };

    public abstract Object parse(String value);

}
