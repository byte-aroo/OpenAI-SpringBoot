package com.openai.openaimaven.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AiModelRequestDTO {
    private String modelId;
    private Boolean baseModel;
    private String trainedFrom;
    private String modelInformation;
    private String remarks;
}
