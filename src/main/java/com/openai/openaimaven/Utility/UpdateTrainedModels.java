package com.openai.openaimaven.Utility;

import com.openai.openaimaven.Entity.ActionSetting;
import com.openai.openaimaven.Entity.Actions;
import com.openai.openaimaven.Entity.FineTunes;
import com.openai.openaimaven.Entity.UploadedFiles;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Repository.ActionSettingRepository;
import com.openai.openaimaven.Repository.ActionsRepository;
import com.openai.openaimaven.Repository.FineTuneRepository;
import com.openai.openaimaven.Repository.UploadedFilesRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class UpdateTrainedModels {

    @Autowired
    private ActionsRepository actionsRepository;

    @Autowired
    private ActionSettingRepository actionSettingRepository;

    @Autowired
    private FineTuneRepository fineTuneRepository;

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    @Value("${open-ai-retrive-fine-tune-model-name}")
    private String newModelName;

    @Scheduled(cron = "0 0 0/1 1/1 * ?")
//    0 0 0/1 1/1 * ?  for 1 hour
//    0 0/1 * 1/1 * ? for 1 minute
    public void updateActionSettingsId()
    {
        List<FineTunes> fineTunes = fineTuneRepository.findAllBySynced(Boolean.FALSE);

        for(FineTunes fineTune : fineTunes)
        {
            String fineTuneId = fineTune.getFineTuneId();
            String response = openAIRestTemplate.makeRequest("/fine-tunes/"+fineTuneId,null,null, HttpMethod.GET);
            JSONObject jsonObject = ParserUtility.stringToJson(response);
            if(jsonObject.get(newModelName)!=null && !jsonObject.get(newModelName).toString().isEmpty())
            {
                fineTune.setModelName(jsonObject.get(newModelName).toString());
                fineTune.setSynced(Boolean.TRUE);
                fineTuneRepository.save(fineTune);
                updateSettingID(fineTune);
            }
        }
    }

    private void updateSettingID(FineTunes fineTunes)
    {
        Optional<ActionSetting> actionSetting = actionSettingRepository.findById(fineTunes.getActionSettingId());
        if(actionSetting.isPresent())
        {
            actionSetting.get().setModel(fineTunes.getModelName());
            actionSettingRepository.save(actionSetting.get());
            Actions actions = actionsRepository.findByActionAndState(fineTunes.getAction(),Boolean.TRUE);
            if(actions!=null)
            {
                actions.setActionSettingsId(fineTunes.getActionSettingId());
                actionsRepository.save(actions);
            }
            else
            {
                log.error("No Action Found with name : "+fineTunes.getAction());
            }
        }
        else
        {
            log.error("Action Setting not found for ID : "+fineTunes.getActionSettingId());
        }
    }

}
