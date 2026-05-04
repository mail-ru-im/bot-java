package ru.mail.im.botapi.api.entity;

import java.io.File;
import java.util.List;

public class SendVoiceRequest {
    private String chatId;
    private File file;
    private List<Long> replyMsgId;
    private String forwardChatId;
    private List<Long> forwardMsgId;
    private List<List<InlineKeyboardButton>> keyboard;

    public String getChatId() {
        return chatId;
    }

    public SendVoiceRequest setChatId(final String chatId) {
        this.chatId = chatId;
        return this;
    }

    public File getFile() {
        return file;
    }

    public SendVoiceRequest setFile(final File file) {
        this.file = file;
        return this;
    }

    public List<Long> getReplyMsgId() {
        return replyMsgId;
    }

    public SendVoiceRequest setReplyMsgId(final List<Long> replyMsgId) {
        this.replyMsgId = replyMsgId;
        return this;
    }

    public String getForwardChatId() {
        return forwardChatId;
    }

    public SendVoiceRequest setForwardChatId(final String forwardChatId) {
        this.forwardChatId = forwardChatId;
        return this;
    }

    public List<Long> getForwardMsgId() {
        return forwardMsgId;
    }

    public SendVoiceRequest setForwardMsgId(final List<Long> forwardMsgId) {
        this.forwardMsgId = forwardMsgId;
        return this;
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public SendVoiceRequest setKeyboard(final List<List<InlineKeyboardButton>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }
}
