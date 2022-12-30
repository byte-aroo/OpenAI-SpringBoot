package com.openai.openaimaven.Entity;

import com.openai.openaimaven.Common.Enums.ActionsEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
public class FineTunes extends BaseEntity{
    private String fineTuneId;
    private String modelName;
    private Long actionSettingId;
    @Enumerated(EnumType.STRING)
    private ActionsEnum action;
    private Boolean synced;
}
