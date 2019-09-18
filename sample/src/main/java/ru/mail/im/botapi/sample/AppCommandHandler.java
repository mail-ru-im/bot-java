package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.sample.command.CommandHandler;
import ru.mail.im.botapi.util.ListenerDescriptor;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class AppCommandHandler implements CommandHandler {

    @Nullable
    private BotApiClient client;

    @Nullable
    private ListenerDescriptor fetchListenerDescriptor;

    @Nullable
    private FileFetchWriter fetchWriter;

    private final List<OnRequestExecuteListener> requestExecuteListeners = new ArrayList<>();

    void addOnRequestExecuteListener(final OnRequestExecuteListener listener) {
        requestExecuteListeners.add(listener);
    }

    @Override
    public void onStart(final String token) {
        client = new BotApiClient("https://u.icq.net/rapi/botapi", token, 0, 1500);
        client.start();
    }

    @Override
    public void onExit() {
        if (client != null) {
            client.stop();
            client = null;
        }
        if (fetchListenerDescriptor != null) {
            fetchListenerDescriptor.remove();
        }
        if (fetchWriter != null) {
            fetchWriter.close();
        }
    }

    @Override
    public void onSelf() {
        api(client -> client.self().get());
    }

    @Override
    public void onSendText(final String chatId, final String text) {
        api(client -> client.messages().sendText(chatId, text));
    }

    @Override
    public void onSendFile(final String chatId, final File file) {
        api(client -> client.messages().sendFile(chatId, file, null));
    }

    @Override
    public void onSendFile(final String chatId, final File file, final String caption) {
        api(client -> client.messages().sendFile(chatId, file, caption));
    }

    @Override
    public void onSendVoice(final String chatId, final File file) {
        System.out.format("Send voice '%s' to chat %s%n", file, chatId);
    }

    @Override
    public void onEditText(final String chatId, final long msgId, final String newText) {
        api(client -> client.messages().editText(chatId, msgId, newText));
    }

    @Override
    public void onDelete(final String chatId, final long msgId) {
        api(client -> client.messages().deleteMessages(chatId, msgId));
    }

    @Override
    public void onDelete(final String chatId, final long[] msgIds) {
        api(client -> client.messages().deleteMessages(chatId, msgIds));
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
        api(client -> client.chats().sendActions(chatId, actions));
    }

    @Override
    public void onGetChatInfo(final String chatId) {
        api(client -> client.chats().getInfo(chatId));
    }

    @Override
    public void onGetChatAdmins(final String chatId) {
        api(client -> client.chats().getAdmins(chatId));
    }

    @Override
    public void onFetch(final File toFile) {
        try {
            fetchWriter = new FileFetchWriter(toFile);
            internal(client -> fetchListenerDescriptor = client.addOnEventFetchListener(events -> fetchWriter.write(events)));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private <T extends ApiResponse> void api(ApiOperation<T> operation) {
        if (client == null) {
            throw new IllegalStateException("Client not started");
        }
        try {
            invokeListeners(operation.execute(client));
        } catch (IOException e) {
            System.err.println("Fail to execute request: " + e.getMessage());
        }
    }

    private void internal(InternalOperation operation) {
        if (client == null) {
            throw new IllegalStateException("Client not started");
        }
        operation.execute(client);
    }

    private void invokeListeners(final ApiResponse response) {
        for (OnRequestExecuteListener listener : requestExecuteListeners) {
            listener.onRequestExecute(response);
        }
    }

    @FunctionalInterface
    private interface ApiOperation<T extends ApiResponse> {
        T execute(BotApiClient client) throws IOException;
    }

    @FunctionalInterface
    private interface InternalOperation {
        void execute(BotApiClient client);
    }
}
