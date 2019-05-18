package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.ChatAction;
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
        withClient(client -> client.self().get());
    }

    @Override
    public void onSendText(final String chatId, final String text) {
        withClient(client -> client.messages().sendText(chatId, text));
    }

    @Override
    public void onSendFile(final String chatId, final File file) {
        withClient(client -> client.messages().sendFile(chatId, file));
    }

    @Override
    public void onSendFile(final String chatId, final File file, final String caption) {
        withClient(client -> client.messages().sendFile(chatId, file, caption));
    }

    @Override
    public void onSendVoice(final String chatId, final File file) {
        System.out.format("Send voice '%s' to chat %s%n", file, chatId);
    }

    @Override
    public void onEditText(final String chatId, final long msgId, final String newText) {
        withClient(client -> client.messages().editText(chatId, msgId, newText));
    }

    @Override
    public void onDelete(final String chatId, final long msgId) {
        withClient(client -> client.messages().deleteMessages(chatId, msgId));
    }

    @Override
    public void onDelete(final String chatId, final long[] msgIds) {
        withClient(client -> client.messages().deleteMessages(chatId, msgIds));
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

    @Override
    public void onChatAction(final String chatId, final ChatAction... actions) {
        withClient(client -> client.chats().sendActions(chatId, actions));
    }

    private <T extends ApiResponse> void withClient(ExecuteListener<T> listener) {
        if (client == null) {
            throw new IllegalStateException("Client not started");
        }
        try {
            invokeListeners(listener.execute(client));
        } catch (IOException e) {
            System.err.println("Fail to execute request: " + e.getMessage());
        }
    }

    private void invokeListeners(final ApiResponse response) {
        for (OnRequestExecuteListener listener : requestExecuteListeners) {
            listener.onRequestExecute(response);
        }
    }

    @FunctionalInterface
    private interface ExecuteListener<T extends ApiResponse> {
        T execute(BotApiClient client) throws IOException;
    }
}
