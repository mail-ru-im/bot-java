package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.sample.command.CommandListener;

import java.io.File;

public class AppCommandListener implements CommandListener {
    @Override
    public void onStart(final String token) {
        System.out.format("start with token='%s'.%n", token);
    }

    @Override
    public void onSelf() {
        System.out.format("self info");
    }

    @Override
    public void onSendText(final String contactId, final String text) {
        System.out.format("Send text '%s' to contact %s%n", text, contactId);
    }

    @Override
    public void onSendFile(final String contactId, final File file) {
        System.out.format("Send file '%s' to contact %s%n", file, contactId);
    }

    @Override
    public void onSendVoice(final String contactId, final File file) {
        System.out.format("Send voice '%s' to contact %s%n", file, contactId);
    }
}
