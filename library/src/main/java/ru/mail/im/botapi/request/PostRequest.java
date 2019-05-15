package ru.mail.im.botapi.request;

import ru.mail.im.botapi.MultipartFormDataBuilder;
import ru.mail.im.botapi.response.ApiResponse;

import java.io.IOException;

public abstract class PostRequest<T extends ApiResponse> extends ApiRequest<T> {
    PostRequest(final String name, final Class<T> responseClass) {
        super(name, responseClass);
    }

    public void buildBody(MultipartFormDataBuilder builder) throws IOException {
        for (QueryParameter parameter : params) {
            builder.addPart(parameter.key, parameter.value);
        }
    }
}
