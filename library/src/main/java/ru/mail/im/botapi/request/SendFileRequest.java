package ru.mail.im.botapi.request;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SendFileRequest extends AbstractMessageRequest {

    private final File file;

    private SendFileRequest(final String chatId, final File file) {
        super("/messages/sendFile", chatId);
        this.file = file;
    }

    @Nullable
    @Override
    public RequestBody buildBody() throws IOException {
        return new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(null, readFileContent(file)))
                .build();
    }

    private static byte[] readFileContent(final File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    public static SendFileRequest simple(final String chatId, final File file) {
        return new SendFileRequest(chatId, file);
    }

    public static SendFileRequest withCaption(final String chatId, final File file, final String caption) {
        final SendFileRequest request = new SendFileRequest(chatId, file);
        request.addParam("caption", caption);
        return request;
    }
}
