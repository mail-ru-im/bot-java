package ru.mail.im.botapi.request;

import ru.mail.im.botapi.GsonResponseParser;
import ru.mail.im.botapi.ResponseParser;
import ru.mail.im.botapi.response.GeneralResponse;

import java.util.HashMap;
import java.util.Map;

public class GeneralRequest<T extends GeneralResponse> {

    private final String name;

    private final Class<T> responseClass;

    private final Map<String, Object> params = new HashMap<>();

    GeneralRequest(final String name, final Class<T> responseClass) {
        this.name = name;
        this.responseClass = responseClass;
    }

    public void prepare(final HttpRequestBuilder builder) {
        builder.addPath(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addQueryParam(entry.getKey(), entry.getValue());
        }
    }

    void addParam(final String name, final Object value) {
        params.put(name, value);
    }

    public ResponseParser<T> getResponseParser() {
        return new GsonResponseParser<>(responseClass);
    }
}
