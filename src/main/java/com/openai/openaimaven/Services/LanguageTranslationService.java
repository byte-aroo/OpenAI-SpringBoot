package com.openai.openaimaven.Services;

import com.openai.openaimaven.Common.Exceptions.DataValidationException;
import com.openai.openaimaven.Common.Enums.ActionsEnum;
import com.openai.openaimaven.DTO.RequestDTO.CompletionRequestDTO;
import com.openai.openaimaven.DTO.RequestDTO.LanguageTransalationRequestDTO;
import com.openai.openaimaven.Entity.ActionSetting;
import com.openai.openaimaven.Entity.Actions;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Repository.ActionSettingRepository;
import com.openai.openaimaven.Repository.ActionsRepository;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class LanguageTranslationService {

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private ActionSettingRepository actionSettingRepository;

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    @Value("${open-ai-language-translation-start-sequence}")
    private String startSequence;

    @Value("${open-ai-language-translation-message-separator}")
    private String messageSeparator;

    @Value("${open-ai-language-translation-stop-sequence}")
    private String stopSequence;



    public List<String> getTranslation(LanguageTransalationRequestDTO transalationRequestDTO)
    {
        Actions actions = actionsRepository.findByActionAndState(ActionsEnum.LANGUAGE_TRANSLATION,Boolean.TRUE);
        if(actions!=null || actions.getActionSettingsId()!=null)
        {
            ActionSetting actionSetting = actionSettingRepository.findById(actions.getActionSettingsId()).orElseThrow(() -> new DataValidationException("No setting found for language translation"));
            CompletionRequestDTO completionRequestDTO = ParserUtility.extractObject(actionSetting,CompletionRequestDTO.class);
            completionRequestDTO.setPrompt(startSequence+transalationRequestDTO.getConvertLanguage()+messageSeparator+transalationRequestDTO.getTextToConvert());
            String translation = openAIRestTemplate.makeRequest("/completions",ParserUtility.objectToJson(completionRequestDTO),null,HttpMethod.POST);
            JSONObject jsonObject = ParserUtility.stringToJson(translation);
            JSONArray choices = (JSONArray) jsonObject.get("choices");
            jsonObject = (JSONObject) choices.get(0);
            return Arrays.asList(jsonObject.get("text").toString().strip().split(stopSequence));
        }
        throw new DataValidationException("No Action Found");
    }
}
