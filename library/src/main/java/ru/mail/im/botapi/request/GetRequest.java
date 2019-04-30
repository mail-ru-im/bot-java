package ru.mail.im.botapi.request;

import ru.mail.im.botapi.QueryStringBuilder;
import ru.mail.im.botapi.response.ApiResponse;

import java.util.Map;

public abstract class GetRequest<T extends ApiResponse> extends ApiRequest<T> {

    GetRequest(final String name, final Class<T> responseClass) {
        super(name, responseClass);
    }

    public void buildQueryString(final QueryStringBuilder builder) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue());
        }
    }
}
