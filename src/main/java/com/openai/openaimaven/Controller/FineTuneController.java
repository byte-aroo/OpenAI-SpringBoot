package com.openai.openaimaven.Controller;

import com.openai.openaimaven.Common.Exceptions.DataValidationException;
import com.openai.openaimaven.DTO.RequestDTO.FineTuneRequestDTO;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.FineTuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/train")
public class FineTuneController extends ApiRestHandler{

    @Autowired
    private FineTuneService fineTuneService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    public ResponseWrapper fineTuneDataset(@RequestBody FineTuneRequestDTO fineTuneRequestDTO)
    {
            return ResponseWrapper.getSuccessResponse(Collections.singletonMap("Fine-Tune Details",fineTuneService.createFineTune(fineTuneRequestDTO)),"Open-AI Fine-Tune Process");
    }

}
