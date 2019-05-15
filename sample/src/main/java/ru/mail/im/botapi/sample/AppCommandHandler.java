package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.request.ApiRequest;
import ru.mail.im.botapi.request.DeleteMessageRequest;
import ru.mail.im.botapi.request.EditTextRequest;
import ru.mail.im.botapi.request.SelfGetRequest;
import ru.mail.im.botapi.request.SendFileRequest;
import ru.mail.im.botapi.request.SendTextRequest;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.sample.command.CommandHandler;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AppCommandHandler implements CommandHandler {

    @Nullable
    private BotApiClient client;

    private final List<OnRequestExecuteListener> requestExecuteListeners = new ArrayList<>();

    void addOnRequestExecuteListener(final OnRequestExecuteListener listener) {
        requestExecuteListeners.add(listener);
    }

    @Override
    public void onStart(final String token) {
        client = new BotApiClient("https://u.icq.net/rapi/botapi", token);
        client.start();
    }

    @Override
    public void onExit() {
        if (client != null) {
            client.stop();
            client = null;
        }
    }

    @Override
    public void onSelf() {
        execute(new SelfGetRequest());
    }

    @Override
    public void onSendText(final String chatId, final String text) {
        execute(SendTextRequest.simple(chatId, text));
    }

    @Override
    public void onSendFile(final String chatId, final File file) {
        execute(SendFileRequest.simple(chatId, file));
    }

    @Override
    public void onSendFile(final String chatId, final File file, final String caption) {
        execute(SendFileRequest.withCaption(chatId, file, caption));
    }

    @Override
    public void onSendVoice(final String chatId, final File file) {
        System.out.format("Send voice '%s' to chat %s%n", file, chatId);
    }

    @Override
    public void onEditText(final String chatId, final long msgId, final String newText) {
        execute(EditTextRequest.create(chatId, msgId, newText));
    }

    @Override
    public void onDelete(final String chatId, final long msgId) {
        execute(DeleteMessageRequest.create(chatId, msgId));
    }

    @Override
    public void onDelete(final String chatId, final long[] msgIds) {
        execute(DeleteMessageRequest.create(chatId, msgIds));
    }

    @Override
    public void onSleep(final long ms) {
        try {
            System.out.println("Sleep for " + ms + " ms");
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }

    private <T extends ApiResponse> void execute(final ApiRequest<T> request) {
        if (client != null) {
            try {
                invokeListeners(client.execute(request));
            } catch (IOException ex) {
                System.err.println("Fail to execute API method: " + ex.getMessage());
            }
        } else {
            System.err.println("Client not started");
        }
    }

    private void invokeListeners(final ApiResponse response) {
        for (OnRequestExecuteListener listener : requestExecuteListeners) {
            listener.onRequestExecute(response);
        }
    }
}
