package com.openai.openaimaven.Common.Exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class DataValidationException extends RuntimeException {
    private static final long serialVersionUID = -526313233133876822L;

    Map<String, String> map = new HashMap<>();

    public Map<String, String> getData() {
        return this.map;
    }

    public DataValidationException(String message, Map<String, String> errorMap) {
        super(message);
        this.map = errorMap;
    }

    public DataValidationException(String message) {
        super(message);
    }

}
