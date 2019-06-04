package ru.mail.im.botapi.sample.command;

import ru.mail.im.botapi.entity.ChatAction;

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

    void onSleep(long ms);

    void onChatAction(String chatId, ChatAction... actions);

    void onGetChatInfo(String chatId);

    void onGetChatAdmins(String chatId);

    void onFetch(File toFile);
}
