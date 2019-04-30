package ru.mail.im.botapi.request;

import ru.mail.im.botapi.response.MessageResponse;

abstract class AbstractMessageRequest extends GeneralRequest<MessageResponse> {
    AbstractMessageRequest(final String name, final String chatId) {
        super(name, MessageResponse.class);
        addParam("chatId", chatId);
    }
}
