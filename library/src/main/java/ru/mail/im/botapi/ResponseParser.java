package ru.mail.im.botapi;

import ru.mail.im.botapi.response.ApiResponse;

import java.io.Reader;

public interface ResponseParser<T extends ApiResponse> {
    T parse(Reader reader);
}
