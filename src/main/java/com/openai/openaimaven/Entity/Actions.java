package com.openai.openaimaven.Entity;

import com.openai.openaimaven.Common.Enums.ActionsEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@Entity
public class Actions extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private ActionsEnum action;
    private Long actionSettingsId;
    private Boolean state;
}
