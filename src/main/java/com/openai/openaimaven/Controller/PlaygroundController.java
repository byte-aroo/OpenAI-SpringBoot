package com.openai.openaimaven.Controller;


import com.openai.openaimaven.DTO.RequestDTO.CompletionRequestDTO;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.PlaygroundServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class PlaygroundController extends ApiRestHandler{

    @Autowired
    private PlaygroundServices playgroundServices;

    @RequestMapping(value = "/playground",method = RequestMethod.POST)
    public ResponseWrapper openAIPlayground(@RequestBody CompletionRequestDTO completionRequestDTO)
    {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("PlaygroundResult",playgroundServices.openAIPlaygroundV2(completionRequestDTO)),"OpenAI Playground");
    }


}
