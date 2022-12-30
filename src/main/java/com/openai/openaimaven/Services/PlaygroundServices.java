package com.openai.openaimaven.Services;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.openaimaven.DTO.RequestDTO.CompletionRequestDTO;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;


@Service
@Slf4j
public class PlaygroundServices {

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

   //TODO: check completionRequest and standardise its values according to OpenAI values (range)
    private HashMap<String,Object> completionRequestDTOValidation(CompletionRequestDTO completionRequestDTO)
    {
        return null;
    }

    public Object openAIPlaygroundV2(CompletionRequestDTO completionRequestDTO)
    {
        //TODO : check completionDTO using completionRequestDTOValidation
        JSONObject playgroundBody = ParserUtility.objectToJson(completionRequestDTO);
        String playgroundOutput = openAIRestTemplate.makeRequest("/completions",playgroundBody,null,HttpMethod.POST);
        JSONObject jsonObject = ParserUtility.stringToJson(playgroundOutput);
        return jsonObject;
    }



}
