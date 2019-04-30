package ru.mail.im.botapi.request;

public class SendTextRequest extends AbstractMessageRequest {

    private SendTextRequest(final String chatId, final String text) {
        super("messages/sendText", chatId);
        addParam("text", text);
    }

    public static SendTextRequest simple(final String chatId, final String text) {
        return new SendTextRequest(chatId, text);
    }
}
