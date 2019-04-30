package ru.mail.im.botapi.request;

public interface HttpRequestBuilder {
    void addPath(String path);

    void addQueryParam(String name, Object value);
}
