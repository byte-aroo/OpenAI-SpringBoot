package com.openai.openaimaven.Controller;

import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.OpenAIModelService;
import com.openai.openaimaven.Utility.ParserUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/models")
public class OpenAIModelController extends ApiRestHandler{

    @Autowired
    private OpenAIModelService openAIModelService;

    @RequestMapping(value = {"","/{modelId}"},method = RequestMethod.GET)
    public ResponseWrapper getOpenAIModels(@PathVariable Optional<String> modelId ){
        if(!modelId.isPresent())
        {
            return ResponseWrapper.getSuccessResponse(Collections.singletonMap("ModelsList",openAIModelService.getListOfAllModels()),"Models Available on OpenAI for this TOKEN");
        }
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("ModelInformation",openAIModelService.getModelById(modelId.get())),"Details of give Model");
    }

}
