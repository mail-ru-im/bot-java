package ru.mail.im.botapi.sample.command;

import java.io.File;

public interface CommandListener {
    void onStart(String token);

    void onExit();

    void onSelf();

    void onSendText(String chatId, String text);

    void onSendFile(String chatId, File file);

    void onSendVoice(String chatId, File file);
}
