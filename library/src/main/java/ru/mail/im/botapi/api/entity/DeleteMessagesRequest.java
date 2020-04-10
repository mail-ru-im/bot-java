package ru.mail.im.botapi.api.entity;

import java.util.List;

public class DeleteMessagesRequest {
    private String chatId;
    private List<Long> msgId;

    public String getChatId() {
        return chatId;
    }

    public DeleteMessagesRequest setChatId(final String chatId) {
        this.chatId = chatId;
        return this;
    }

    public List<Long> getMsgId() {
        return msgId;
    }

    public DeleteMessagesRequest setMsgId(final List<Long> msgId) {
        this.msgId = msgId;
        return this;
    }
}
