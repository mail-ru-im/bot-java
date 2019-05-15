package ru.mail.im.botapi.request;

import ru.mail.im.botapi.response.ApiResponse;

public class DeleteMessageRequest extends GetRequest<ApiResponse> {

    private DeleteMessageRequest() {
        super("messages/deleteMessages", ApiResponse.class);
    }

    public static DeleteMessageRequest create(final String chatId, final long... msgIds) {
        final DeleteMessageRequest request = new DeleteMessageRequest();
        request.addParam("chatId", chatId);
        for (long msgId : msgIds) {
            request.addParam("msgId", msgId);
        }
        return request;
    }
}
