package com.openai.openaimaven.Services;


import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAIModelService {

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    public Object getListOfAllModels()
    {
        String modelsList = openAIRestTemplate.makeRequest("/models",null,null,HttpMethod.GET);
        return ParserUtility.stringToJson(modelsList);
    }

    public Object getModelById(String modelId)
    {
        String modelDetail = openAIRestTemplate.makeRequest("/models/"+modelId,null,null,HttpMethod.GET);
        return ParserUtility.stringToJson(modelDetail);
    }

}
