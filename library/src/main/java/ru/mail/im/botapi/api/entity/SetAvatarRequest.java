package ru.mail.im.botapi.api.entity;

import java.io.File;

public class SetAvatarRequest {

    private String chatId;
    private File avatar;

    public String getChatId() {
        return chatId;
    }

    public SetAvatarRequest setChatId(final String chatId) {
        this.chatId = chatId;
        return this;
    }

    public File getAvatar() {
        return avatar;
    }

    public SetAvatarRequest setAvatar(final File avatar) {
        this.avatar = avatar;
        return this;
    }
}
