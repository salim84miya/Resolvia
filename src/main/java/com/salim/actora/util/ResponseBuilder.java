package com.salim.actora.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class ResponseBuilder {

    public static ResponseEntity<?> build(
            Object body,
            Map<String,String> errors,
            HttpStatus httpStatus,
            LocalDateTime createdAt
    ){

        Map<String ,Object> result = new HashMap<>();

        result.put("body",body);
        result.put("errors",errors);
        result.put("httpStatus",httpStatus);
        result.put("createdAt",createdAt);

        return new ResponseEntity<>(result,httpStatus);

    }
}
