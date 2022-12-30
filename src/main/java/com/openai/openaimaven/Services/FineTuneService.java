package com.openai.openaimaven.Services;

import com.openai.openaimaven.Common.Exceptions.DataValidationException;
import com.openai.openaimaven.Common.Exceptions.InvalidDataException;
import com.openai.openaimaven.DTO.RequestDTO.FineTuneRequestDTO;
import com.openai.openaimaven.DTO.RequestDTO.OpenAIFineTuneRequestDTO;
import com.openai.openaimaven.Entity.ActionSetting;
import com.openai.openaimaven.Entity.Actions;
import com.openai.openaimaven.Entity.FineTunes;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Repository.ActionSettingRepository;
import com.openai.openaimaven.Repository.ActionsRepository;
import com.openai.openaimaven.Repository.FineTuneRepository;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FineTuneService {

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    @Autowired
    private FineTuneRepository fineTuneRepository;

    @Autowired
    private ActionSettingRepository actionSettingRepository;

    @Autowired
    private ActionsRepository actionsRepository;

    private void validateActionSetting(FineTuneRequestDTO fineTuneRequestDTO)
    {
        if(fineTuneRequestDTO.getActionSetting()==null || fineTuneRequestDTO.getAction()==null)
        {
            throw new DataValidationException("Provide Action Type and Action Settings ID");
        }
        ActionSetting setting = actionSettingRepository.findById(fineTuneRequestDTO.getActionSetting()).orElseThrow(()->new InvalidDataException("Settings does not exist."));
        Actions actions = actionsRepository.findByActionAndState(fineTuneRequestDTO.getAction(),Boolean.TRUE);
        if(actions==null)
        {
            throw new InvalidDataException("Action or Action Setting does not exist");
        }
    }

    private void validateFineTuneRequestDTO(FineTuneRequestDTO fineTuneRequestDTO)
    {

    }

    public JSONObject createFineTune(FineTuneRequestDTO fineTuneRequestDTO)
    {
        //TODO : Validate fineTuneRequestDTO
        validateActionSetting(fineTuneRequestDTO);
        OpenAIFineTuneRequestDTO openAIFineTuneRequestDTO = ParserUtility.extractObject(fineTuneRequestDTO,OpenAIFineTuneRequestDTO.class);
        JSONObject fineTuneRequest = ParserUtility.objectToJson(openAIFineTuneRequestDTO);
        String response = openAIRestTemplate.makeRequest("/fine-tunes",fineTuneRequest,null,HttpMethod.POST);
        JSONObject fineTuneResponseObject = ParserUtility.stringToJson(response);
        saveFineTune(fineTuneResponseObject,fineTuneRequestDTO);
        return fineTuneResponseObject;
    }

    private void saveFineTune(JSONObject jsonObject,FineTuneRequestDTO fineTuneRequestDTO)
    {
        FineTunes fineTunes = new FineTunes();

        if(jsonObject.get("id")==null || jsonObject.get("id").toString().isEmpty()) {
            throw new InvalidDataException("Unable to create fine tune");
        }
        if(jsonObject.get("fine_tuned_model")!=null) {
            fineTunes.setModelName(jsonObject.get("fine_tuned_model").toString());
        }

        fineTunes.setFineTuneId(jsonObject.get("id").toString());
        fineTunes.setActionSettingId(fineTuneRequestDTO.getActionSetting());
        fineTunes.setAction(fineTuneRequestDTO.getAction());
        fineTunes.setSynced(Boolean.FALSE);

        fineTuneRepository.save(fineTunes);
    }
}
