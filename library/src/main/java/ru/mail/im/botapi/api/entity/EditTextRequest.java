package ru.mail.im.botapi.api.entity;

import java.util.List;

public class EditTextRequest {
    private String chatId;
    private long msgId;
    private String newText;
    private List<List<InlineKeyboardButton>> keyboard;

    public String getChatId() {
        return chatId;
    }

    public EditTextRequest setChatId(final String chatId) {
        this.chatId = chatId;
        return this;
    }

    public long getMsgId() {
        return msgId;
    }

    public EditTextRequest setMsgId(final long msgId) {
        this.msgId = msgId;
        return this;
    }

    public String getNewText() {
        return newText;
    }

    public EditTextRequest setNewText(final String newText) {
        this.newText = newText;
        return this;
    }

    public List<List<InlineKeyboardButton>> getKeyboard() {
        return keyboard;
    }

    public EditTextRequest setKeyboard(final List<List<InlineKeyboardButton>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }
}
