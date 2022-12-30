package com.openai.openaimaven.Controller;

import com.openai.openaimaven.Common.Exceptions.InvalidDataException;
import com.openai.openaimaven.DTO.RequestDTO.ChatBotRequestDTO;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/chatbot")
public class ChatBotController extends ApiRestHandler{

    @Autowired
    private ChatBotService chatBotService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseWrapper chatBotRequest(@RequestBody ChatBotRequestDTO chatBotRequestDTO)
    {
        if(chatBotRequestDTO!=null)
        {
            return ResponseWrapper.getSuccessResponse(Collections.singletonMap("ChatBot",chatBotService.chatBotRequestService(chatBotRequestDTO)), "Open-AI Chat Bot");
        }
        throw new InvalidDataException("No Message Received From Human");
    }

}
