package com.safetycar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class TestHelper {
    public static final char WHY_JSON = '"';

    public static String getJsonPropertyValueAsString(ObjectMapper objectMapper,
                                                      String body,
                                                      String property) throws JsonProcessingException {
        return objectMapper.writeValueAsString(JsonPath.read(body, "$." + property));
    }
}
