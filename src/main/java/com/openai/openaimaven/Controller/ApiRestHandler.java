package com.openai.openaimaven.Controller;

import com.openai.openaimaven.Common.Exceptions.DataValidationException;
import com.openai.openaimaven.Common.Exceptions.InvalidDataException;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;


@ControllerAdvice(annotations = RestController.class)
@Slf4j
public abstract class ApiRestHandler implements ApplicationEventPublisherAware {

    protected ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }


    @ExceptionHandler(ParseException.class)
    public @ResponseBody
    ResponseWrapper handleStringToJsonParsingError(ParseException pe)
    {
        return new ResponseWrapper(false, "Error in Parsing String to JSON");
    }

    @ExceptionHandler(DataValidationException.class)
    public @ResponseBody
    ResponseWrapper dataValidationException(DataValidationException e)
    {
        return new ResponseWrapper(false, e.getMessage());
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public @ResponseBody
    ResponseWrapper clientErrorException(HttpClientErrorException e)
    {
        return new ResponseWrapper(false, ParserUtility.stringToJson(e.getResponseBodyAsString()));
    }

    @ExceptionHandler(IOException.class)
    public @ResponseBody
    ResponseWrapper fileHandlingException(IOException ioException)
    {
        return new ResponseWrapper(false,ioException.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public @ResponseBody
    ResponseWrapper invalidDataException(InvalidDataException idException)
    {
        return new ResponseWrapper(false,idException.getMessage());
    }

}
