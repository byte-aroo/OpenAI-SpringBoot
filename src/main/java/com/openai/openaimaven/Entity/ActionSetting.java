package com.openai.openaimaven.Entity;


import lombok.Data;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

@Data
@Entity
@ToString
public class ActionSetting extends BaseEntity{
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
    @ElementCollection
    private Map<String,Integer> logitBias;
    private String user;
    private Boolean state;
}
