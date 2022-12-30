package com.openai.openaimaven.Common.Exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = -526313233133876823L;

    Map<String, String> map = new HashMap<>();

    public Map<String, String> getData() {
        return this.map;
    }

    public InvalidDataException(String message, Map<String, String> errorMap) {
        super(message);
        this.map = errorMap;
    }

    public InvalidDataException(String message) {
        super(message);
    }

}
