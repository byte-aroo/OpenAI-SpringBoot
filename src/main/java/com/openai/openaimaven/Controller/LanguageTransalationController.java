package com.openai.openaimaven.Controller;

import com.openai.openaimaven.DTO.RequestDTO.LanguageTransalationRequestDTO;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.LanguageTranslationService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/translation")
public class LanguageTransalationController extends ApiRestHandler{

    @Autowired
    private LanguageTranslationService languageTranslationService;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseWrapper convertLanguage(@RequestBody LanguageTransalationRequestDTO transalationRequestDTO)
    {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("Translation",languageTranslationService.getTranslation(transalationRequestDTO)),"Open-AI Language Translation");
    }


}
