package ru.mail.im.botapi.request;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import ru.mail.im.botapi.GsonResponseParser;
import ru.mail.im.botapi.ResponseParser;
import ru.mail.im.botapi.response.GeneralResponse;

import javax.annotation.Nullable;
import java.io.IOException;
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

    public void buildUrl(final HttpUrl.Builder builder) {
        builder.addPathSegment(name);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addQueryParameter(entry.getKey(), entry.getValue() == null ? null : entry.getValue().toString());
        }
    }

    @Nullable
    public RequestBody buildBody() throws IOException {
        return null;
    }

    void addParam(final String name, final Object value) {
        params.put(name, value);
    }

    public ResponseParser<T> getResponseParser() {
        return new GsonResponseParser<>(responseClass);
    }
}
