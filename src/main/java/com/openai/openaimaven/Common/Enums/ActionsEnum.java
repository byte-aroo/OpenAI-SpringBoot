package com.openai.openaimaven.Common.Enums;

public enum ActionsEnum {
    LANGUAGE_TRANSLATION("language_translation"),
    CHAT_BOT("chat_bot");

    private String action;

    private ActionsEnum(String action)
    {
        this.action=action;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
