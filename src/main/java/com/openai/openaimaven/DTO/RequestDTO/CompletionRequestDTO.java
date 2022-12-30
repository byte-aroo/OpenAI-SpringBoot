package com.openai.openaimaven.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompletionRequestDTO{

    private String model;

    private String prompt;

    private String suffix;

    private Integer maxTokens;

    private Float temperature;

    private Float topP;

    private Integer n;

    private Boolean stream;

    private Integer logprobs;

    private Boolean echo;

    private String stop;

    private Float presencePenalty;

    private Float frequencyPenalty;

    private Integer bestOf;

    private Map<String,String> logitBias;

    private String user;

}
