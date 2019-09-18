package ru.mail.im.botapi;

import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.response.*;

import java.io.File;
import java.io.IOException;

public class BotApiClientController {

    private final BotApiClient client;

    public static BotApiClientController startBot(BotApiClient client) {
        return new BotApiClientController(client);
    }

    private BotApiClientController(BotApiClient client) {
        this.client = client;
        client.start();
    }

    public MessageResponse sendTextMessage(String chatId, String text) throws IOException {
        return client.messages().sendText(chatId, text);
    }

    public MessageResponse replyText(String chatId, String text, long replyMsgId) throws IOException {
        return client.messages().replyText(chatId, text, replyMsgId);
    }

    public MessageResponse forwardText(String chatId,
                                       String text,
                                       String forwardChatId,
                                       long forwardMsgId) throws IOException {
        return client.messages().forwardText(chatId, text, forwardChatId, forwardMsgId);
    }

    public MessageResponse sendFile(String chatId, File file) throws IOException {
        return client.messages().sendFile(chatId, file, null);
    }

    public MessageResponse sendFile(String chatId, File file, String caption) throws IOException {
        return client.messages().sendFile(chatId, file, caption);
    }

    public MessageResponse replyFile(String chatId, File file, long replyMsgId, String caption) throws IOException {
        return client.messages().replyFile(chatId, file, replyMsgId, caption);
    }

    public MessageResponse forwardFile(String chatId,
                                       File file,
                                       String forwardChatId,
                                       long forwardMsgId,
                                       String caption) throws IOException {
        return client.messages().forwardFile(chatId, file, forwardChatId, forwardMsgId, caption);
    }

    public ApiResponse editText(String chatId, long msgId, String text) throws IOException {
        return client.messages().editText(chatId, msgId, text);
    }

    public ApiResponse deleteMessage(String chatId, long msgId) throws IOException {
        return client.messages().deleteMessages(chatId, msgId);
    }

    public ChatsGetAdminsResponse getChatAdmins(String chatId) throws IOException {
        return client.chats().getAdmins(chatId);
    }

    public ChatsGetInfoResponse getChatInfo(String chatId) throws IOException {
        return client.chats().getInfo(chatId);
    }

    public ApiResponse sendActions(String chatId, ChatAction... actions) throws IOException {
        return client.chats().sendActions(chatId, actions);
    }

    public SelfGetResponse getSelfInfo() throws IOException {
        return client.self().get();
    }
}
