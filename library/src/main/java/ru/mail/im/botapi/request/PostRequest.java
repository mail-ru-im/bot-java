package ru.mail.im.botapi.request;

import okhttp3.MultipartBody;
import ru.mail.im.botapi.response.ApiResponse;

import java.io.IOException;
import java.util.Map;

public abstract class PostRequest<T extends ApiResponse> extends ApiRequest<T> {
    PostRequest(final String name, final Class<T> responseClass) {
        super(name, responseClass);
    }

    public void fillBody(MultipartBody.Builder builder) throws IOException {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() != null) {
                builder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }
    }
}
