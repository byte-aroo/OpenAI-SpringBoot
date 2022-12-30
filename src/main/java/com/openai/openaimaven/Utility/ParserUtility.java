package com.openai.openaimaven.Utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Slf4j
public class ParserUtility {

    public static <T> T extractObject(final Object object,final Class<T> type) {
        try {
            return new ObjectMapper().convertValue(object,type);
        }catch (final Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @SneakyThrows
    public static JSONObject stringToJson(String data){
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject ;
        try {
            jsonObject = (JSONObject) jsonParser.parse(data);
        }
        catch (Exception e)
        {
            throw new ParseException(1);
        }
        return jsonObject;
    }

    @SneakyThrows
    public static JSONObject objectToJson(Object o)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String data = objectMapper.writeValueAsString(o);
        return stringToJson(data);
    }

}
