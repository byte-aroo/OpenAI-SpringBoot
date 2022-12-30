package com.openai.openaimaven.DTO.RequestDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.openai.openaimaven.Common.Enums.ActionsEnum;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class FineTuneRequestDTO {
    private String trainingFile;
    private String validationFile;
    private String model;
    private Integer nEpochs;
    private Integer batchSize;
    private Float learningRateMultiplier;
    private Float promptLossWeight;
    private Boolean computeClassificationMetrics;
    private Integer classificationNClasses;
    private String classificationPositiveClass;
    private String classificationBetas;
    private String suffix;
    private Long actionSetting;
    private ActionsEnum action;
}
