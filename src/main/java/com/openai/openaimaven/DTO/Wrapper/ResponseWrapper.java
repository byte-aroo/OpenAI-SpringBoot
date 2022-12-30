package com.openai.openaimaven.DTO.Wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private Map<String,Object> data;

    @JsonProperty("openAIErrorMessage")
    private JSONObject openAIErrorMessage;

    public ResponseWrapper(boolean success, String message) {
        this.success = success;
        this.message=message;
    }

    public ResponseWrapper(boolean success, JSONObject openAIErrorMessage) {
        this.success = success;
        this.openAIErrorMessage= openAIErrorMessage;
    }

    public ResponseWrapper(boolean success, int code, String message) {
        this.success = success;
        this.code=code;
        this.message=message;
    }
    public ResponseWrapper(boolean success, int code, String message, Map<String,Object> data) {
        this.success = success;
        this.code=code;
        this.message=message;
        this.data = data;
    }

    public static ResponseWrapper getSuccessResponse(Map<String, Object> data, String message) {
        return new ResponseWrapper(true, HttpStatus.OK.value(), message, data);
    }

    public static ResponseWrapper getFailureResponse(String message) {
        return new ResponseWrapper(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

}
