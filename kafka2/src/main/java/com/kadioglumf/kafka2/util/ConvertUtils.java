package com.kadioglumf.kafka2.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

@Log4j2
public class ConvertUtils {

    public static <T> T convertJsonDataToObject(String jsonDta, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(jsonDta, clazz);
        } catch (JsonProcessingException ex) {
            ex.getStackTrace();
            log.error("convertJsonDataToObject method exception: ", ex);
        }
        return null;
    }

    public static String convertObjectToJsonData(Object value) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            ex.getStackTrace();
            log.error("convertJsonDataToObject method exception: ", ex);
        }
        return null;
    }

    public static Object getFieldValueByName(String json, String fieldName) {
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has(fieldName)) {
                return obj.getString(fieldName);
            }
        } catch (Exception ex) {
            log.error("not found fieldName: " + fieldName);
        }
        return null;
    }
}

