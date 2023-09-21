package com.example.chatbot;

public class MsgModal {

    private String content;
    private boolean isUserMessage;

    public MsgModal(String content, boolean isUserMessage) {
        this.content = content;
        this.isUserMessage = isUserMessage;
    }

    public String getContent() {
        return content;
    }

    public boolean isUserMessage() {
        return isUserMessage;
    }
}
