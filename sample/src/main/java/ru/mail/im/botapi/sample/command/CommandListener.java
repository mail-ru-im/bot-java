package ru.mail.im.botapi.sample.command;

import java.io.File;

public interface CommandListener {
    void onStart(String token);

    void onSelf();

    void onSendText(String contactId, String text);

    void onSendFile(String contactId, File file);

    void onSendVoice(String contactId, File file);
}
