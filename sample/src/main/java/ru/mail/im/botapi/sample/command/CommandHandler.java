package ru.mail.im.botapi.sample.command;

import java.io.File;

public interface CommandHandler {
    void onStart(String token);

    void onExit();

    void onSelf();

    void onSendText(String chatId, String text);

    void onSendFile(String chatId, File file);

    void onSendFile(String chatId, File file, String caption);

    void onSendVoice(String chatId, File file);

    void onEditText(String chatId, long msgId, String newText);

    void onDelete(String chatId, long msgId);

    void onDelete(String chatId, long[] msgIds);
}
