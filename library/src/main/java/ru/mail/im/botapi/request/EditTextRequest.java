package ru.mail.im.botapi.request;

import ru.mail.im.botapi.response.ApiResponse;

public class EditTextRequest extends GetRequest<ApiResponse> {

    private EditTextRequest() {
        super("messages/editText", ApiResponse.class);
    }

    public static EditTextRequest create(final String chatId, final long msgId, final String newText) {
        final EditTextRequest request = new EditTextRequest();
        request.addParam("chatId", chatId);
        request.addParam("msgId", msgId);
        request.addParam("text", newText);
        return request;
    }
}
