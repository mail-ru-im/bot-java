package ru.mail.im.botapi.api.entity;

import java.util.List;

public class SendTextRequest {
    private String chatId;
    private String text;
    private List<Long> replyMsgId;
    private String forwardChatId;
    private List<Long> forwardMsgId;
    private List<List<InlineKeyboardButton>> keyboard;

    public String getChatId() {
        return chatId;
    }

    public SendTextRequest setChatId(final String chatId) {
        this.chatId = chatId;
        return this;
    }

    public String getText() {
        return text;
    }

    public SendTextRequest setText(final String text) {
        this.text = text;
        return this;
    }

    public List<Long> getReplyMsgId() {
        return replyMsgId;
    }

    public SendTextRequest setReplyMsgId(final List<Long> replyMsgId) {
        this.replyMsgId = replyMsgId;
        return this;
    }

    public String getForwardChatId() {
        return forwardChatId;
    }

    public SendTextRequest setForwardChatId(final String forwardChatId) {
        this.forwardChatId = forwardChatId;
        return this;
    }

    public List<Long> getForwardMsgId() {
        return forwardMsgId;
    }

    public SendTextRequest setForwardMsgId(final List<Long> forwardMsgId) {
        this.forwardMsgId = forwardMsgId;
        return this;
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public SendTextRequest setKeyboard(final List<List<InlineKeyboardButton>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }
}
