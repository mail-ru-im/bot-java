package ru.mail.im.botapi.request;

import ru.mail.im.botapi.response.MessageResponse;

public class SendTextRequest extends GetRequest<MessageResponse> {

    private SendTextRequest() {
        super("messages/sendText", MessageResponse.class);
    }

    public static SendTextRequest simple(final String chatId, final String text) {
        final SendTextRequest request = new SendTextRequest();
        request.addParam("chatId", chatId);
        request.addParam("text", text);
        return request;
    }
}
