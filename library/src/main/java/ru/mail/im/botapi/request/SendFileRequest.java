package ru.mail.im.botapi.request;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ru.mail.im.botapi.response.MessageResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SendFileRequest extends PostRequest<MessageResponse> {

    private final File file;

    private SendFileRequest(final File file) {
        super("/messages/sendFile", MessageResponse.class);
        this.file = file;
    }

    @Override
    public void fillBody(final MultipartBody.Builder builder) throws IOException {
        super.fillBody(builder);
        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(null, readFileContent(file)));
    }

    private static byte[] readFileContent(final File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public static SendFileRequest simple(final String chatId, final File file) {
        final SendFileRequest request = new SendFileRequest(file);
        request.addParam("chatId", chatId);
        return request;
    }

    public static SendFileRequest withCaption(final String chatId, final File file, final String caption) {
        final SendFileRequest request = new SendFileRequest(file);
        request.addParam("chatId", chatId);
        request.addParam("caption", caption);
        return request;
    }
}
