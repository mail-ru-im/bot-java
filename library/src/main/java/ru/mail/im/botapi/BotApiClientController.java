package ru.mail.im.botapi;

import ru.mail.im.botapi.api.entity.AnswerCallbackQueryRequest;
import ru.mail.im.botapi.api.entity.DeleteMessagesRequest;
import ru.mail.im.botapi.api.entity.EditTextRequest;
import ru.mail.im.botapi.api.entity.SendFileRequest;
import ru.mail.im.botapi.api.entity.SendVoiceRequest;
import ru.mail.im.botapi.api.entity.SendTextRequest;
import ru.mail.im.botapi.api.entity.SetAvatarRequest;
import ru.mail.im.botapi.fetcher.FetchResponse;
import ru.mail.im.botapi.response.FileResponse;
import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.api.entity.ChatMember;
import ru.mail.im.botapi.api.entity.EventRequest;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.ChatsGetAdminsResponse;
import ru.mail.im.botapi.response.ChatsGetInfoResponse;
import ru.mail.im.botapi.response.ChatsGetMembersResponse;
import ru.mail.im.botapi.response.ChatsUsersResponse;
import ru.mail.im.botapi.response.MessageResponse;
import ru.mail.im.botapi.response.SelfGetResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BotApiClientController {

    private final BotApiClient client;

    public static BotApiClientController startBot(BotApiClient client) {
        return new BotApiClientController(client);
    }

    private BotApiClientController(BotApiClient client) {
        this.client = client;
        client.start();
    }

    public MessageResponse sendTextMessage(final SendTextRequest request) throws IOException {
        return client.messages().sendText(
            request.getChatId(),
            request.getText(),
            request.getFormat(),
            request.getParseMode(),
            toLongArray(request.getReplyMsgId()),
            request.getForwardChatId(),
            toLongArray(request.getForwardMsgId()),
            request.getKeyboard()
        );
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendTextMessage(SendTextRequest)}
     */
    @Deprecated
    public MessageResponse sendTextMessage(
        final String chatId,
        final String text
    ) throws IOException {
        return client.messages().sendText(chatId, text, null, null, null, null, null, null);
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendTextMessage(SendTextRequest)}
     */
    @Deprecated
    public MessageResponse replyText(
        final String chatId,
        final String text,
        final long replyMsgId
    ) throws IOException {
        return client.messages().sendText(chatId, text, null, null, new long[]{replyMsgId}, null, null, null);
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendTextMessage(SendTextRequest)}
     */
    @Deprecated
    public MessageResponse forwardText(
        final String chatId,
        final String text,
        final String forwardChatId,
        final long forwardMsgId
    ) throws IOException {
        return client.messages().sendText(chatId, text, null, null, null, forwardChatId, new long[]{forwardMsgId}, null);
    }

    public MessageResponse sendFile(final SendFileRequest request) throws IOException {
        return client.messages().sendFile(
            request.getChatId(),
            request.getFile(),
            request.getCaption(),
            toLongArray(request.getReplyMsgId()),
            request.getForwardChatId(),
            toLongArray(request.getForwardMsgId()),
            request.getKeyboard()
        );
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendFile(SendFileRequest)}
     */
    @Deprecated
    public MessageResponse sendFile(
        final String chatId,
        final File file
    ) throws IOException {
        return client.messages().sendFile(chatId, file, null, null, null, null, null);
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendFile(SendFileRequest)}
     */
    @Deprecated
    public MessageResponse sendFile(
        final String chatId,
        final File file,
        final String caption
    ) throws IOException {
        return client.messages().sendFile(chatId, file, caption, null, null, null, null);
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendFile(SendFileRequest)}
     */
    @Deprecated
    public MessageResponse replyFile(
        final String chatId,
        final File file,
        final long replyMsgId,
        final String caption
    ) throws IOException {
        return client.messages().sendFile(chatId, file, caption, new long[]{replyMsgId}, null, null, null);
    }

    /**
     * @deprecated Use {@link BotApiClientController#sendFile(SendFileRequest)}
     */
    @Deprecated
    public MessageResponse forwardFile(
        final String chatId,
        final File file,
        final String forwardChatId,
        final long forwardMsgId,
        final String caption
    ) throws IOException {
        return client.messages().sendFile(chatId, file, caption, null, forwardChatId, new long[]{forwardMsgId}, null);
    }

    public MessageResponse sendVoice(final SendVoiceRequest request) throws IOException {
        return client.messages().sendVoice(
                request.getChatId(),
                request.getFile(),
                toLongArray(request.getReplyMsgId()),
                request.getForwardChatId(),
                toLongArray(request.getForwardMsgId()),
                request.getKeyboard()
        );
    }

    public ApiResponse editText(final EditTextRequest request) throws IOException {
        return client.messages().editText(
            request.getChatId(),
            request.getMsgId(),
            request.getNewText(),
            request.getFormat(),
            request.getParseMode(),
            request.getKeyboard()
        );
    }

    /**
     * @deprecated Use {@link BotApiClientController#editText(EditTextRequest)}
     */
    @Deprecated
    public ApiResponse editText(
        final String chatId,
        final long msgId,
        final String text
    ) throws IOException {
        return client.messages().editText(chatId, msgId, text, null, null, null);
    }

    public ApiResponse deleteMessage(final DeleteMessagesRequest request) throws IOException {
        return client.messages().deleteMessages(
            request.getChatId(),
            toLongArray(request.getMsgId())
        );
    }

    /**
     * @deprecated Use {@link BotApiClientController#deleteMessage(DeleteMessagesRequest)}
     */
    @Deprecated
    public ApiResponse deleteMessage(
        final String chatId,
        final long msgId
    ) throws IOException {
        return client.messages().deleteMessages(chatId, new long[] {msgId});
    }

    public ApiResponse answerCallbackQuery(final AnswerCallbackQueryRequest request) throws IOException {
        return client.messages().answerCallbackQuery(
            request.getQueryId(),
            request.getText(),
            request.getShowAlert(),
            request.getUrl()
        );
    }

    public FileResponse fileGetInfo(final String fileId) throws IOException {
        return client.files().getInfo(fileId);
    }

    public FetchResponse getEvents(final EventRequest request) throws IOException {
        return client.events().getEvents(request.getLastEventId(), request.getPollTime());
    }

    public ApiResponse deleteFromChat(String chatId, List<ChatMember> members) throws IOException {
        return client.chats().delete(chatId, members);
    }

    public ApiResponse setChatAvatar(SetAvatarRequest request) throws IOException {
        return client.chats().setAvatar(request.getChatId(), request.getAvatar());
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

    public ChatsGetMembersResponse getChatMembers(String chatId, String cursor) throws IOException {
        return client.chats().getMembers(chatId, cursor);
    }

    public ChatsUsersResponse getChatBlockedUsers(String chatId) throws IOException {
        return client.chats().getBlockedUsers(chatId);
    }

    public ChatsUsersResponse getChatPendingUsers(String chatId) throws IOException {
        return client.chats().getPendingUsers(chatId);
    }

    public ApiResponse chatBlockUser(String chatId, String userId, boolean delLastMessages) throws IOException {
        return client.chats().blockUser(chatId, userId, delLastMessages);
    }

    public ApiResponse chatUnblockUser(String chatId, String userId) throws IOException {
        return client.chats().unblockUser(chatId, userId);
    }

    public ApiResponse chatResolvePending(String chatId, String userId, Boolean approve) throws IOException {
        return client.chats().resolvePending(chatId, userId, approve);
    }

    public ApiResponse chatResolvePending(String chatId, Boolean everyone) throws IOException {
        return client.chats().resolvePending(chatId, everyone);
    }

    public ApiResponse chatSetTitle(String chatId, String title) throws IOException {
        return client.chats().setTitle(chatId, title);
    }

    public ApiResponse chatSetAbout(String chatId, String about) throws IOException {
        return client.chats().setAbout(chatId, about);
    }

    public ApiResponse chatSetRules(String chatId, String rules) throws IOException {
        return client.chats().setRules(chatId, rules);
    }

    public ApiResponse chatPinMessage(String chatId, long msgId) throws IOException {
        return client.chats().pinMessage(chatId, msgId);
    }

    public ApiResponse chatUnpinMessage(String chatId, long msgId) throws IOException {
        return client.chats().unpinMessage(chatId, msgId);
    }

    public SelfGetResponse getSelfInfo() throws IOException {
        return client.self().get();
    }

    private long[] toLongArray(final List<Long> longList) {
        if (longList == null) {
            return null;
        } else {
            return longList.stream().mapToLong(msgId -> msgId).toArray();
        }
    }
}
