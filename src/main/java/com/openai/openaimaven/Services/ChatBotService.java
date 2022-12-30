package com.openai.openaimaven.Services;

import com.openai.openaimaven.Common.Enums.ActionsEnum;
import com.openai.openaimaven.Common.Exceptions.DataValidationException;
import com.openai.openaimaven.DTO.RequestDTO.ChatBotRequestDTO;
import com.openai.openaimaven.DTO.RequestDTO.CompletionRequestDTO;
import com.openai.openaimaven.Entity.ActionSetting;
import com.openai.openaimaven.Entity.Actions;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Repository.ActionSettingRepository;
import com.openai.openaimaven.Repository.ActionsRepository;
import com.openai.openaimaven.Utility.ParserUtility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatBotService {

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private ActionSettingRepository actionSettingRepository;

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    @Value("${open-ai-chat-bot-start-sequence}")
    private String startSequence;

    @Value("${open-ai-chat-bot-stop-sequence}")
    private String stopSequence;

    @Value("${open-ai-chat-bot-end-pattern-1}")
    private String endPattern1;

    public List<String> chatBotRequestService(ChatBotRequestDTO chatBotRequestDTO)
    {
        Actions actions = actionsRepository.findByActionAndState(ActionsEnum.CHAT_BOT,Boolean.TRUE);
        if(actions!=null && actions.getActionSettingsId()!=null)
        {
            ActionSetting actionSetting = actionSettingRepository.findById(actions.getActionSettingsId()).orElseThrow(() -> new DataValidationException("No setting found for language translation"));
            CompletionRequestDTO completionRequestDTO = ParserUtility.extractObject(actionSetting,CompletionRequestDTO.class);
            completionRequestDTO.setPrompt(startSequence+chatBotRequestDTO.getHuman()+stopSequence);
            completionRequestDTO.setStop(new ArrayList<>(Arrays.asList(endPattern1)));
            String chatBotResponse = openAIRestTemplate.makeRequest("/completions",ParserUtility.objectToJson(completionRequestDTO),null, HttpMethod.POST);
            JSONObject jsonObject = ParserUtility.stringToJson(chatBotResponse);
            JSONArray choices = (JSONArray)jsonObject.get("choices");
            jsonObject = (JSONObject)choices.get(0);
            return Arrays.asList(jsonObject.get("text").toString().strip());
        }
        throw new DataValidationException("No Action Found");
    }

}
