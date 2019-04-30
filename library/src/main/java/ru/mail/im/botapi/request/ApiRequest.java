package ru.mail.im.botapi.request;

import ru.mail.im.botapi.GsonResponseParser;
import ru.mail.im.botapi.ResponseParser;
import ru.mail.im.botapi.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class ApiRequest<T extends ApiResponse> {

    private final String name;

    private final Class<T> responseClass;

    final Map<String, Object> params = new HashMap<>();

    ApiRequest(final String name, final Class<T> responseClass) {
        this.name = name;
        this.responseClass = responseClass;
    }

    public String getName() {
        return name;
    }

    public ResponseParser<T> getResponseParser() {
        return new GsonResponseParser<>(responseClass);
    }

    void addParam(final String name, final Object value) {
        params.put(name, value);
    }
}
