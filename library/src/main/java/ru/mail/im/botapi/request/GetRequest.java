package ru.mail.im.botapi.request;

import okhttp3.HttpUrl;
import ru.mail.im.botapi.response.ApiResponse;

import java.util.Map;

public abstract class GetRequest<T extends ApiResponse> extends ApiRequest<T> {

    GetRequest(final String name, final Class<T> responseClass) {
        super(name, responseClass);
    }

    public void fillQueryString(final HttpUrl.Builder builder) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
        }
    }
}
