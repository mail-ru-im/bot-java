package ru.mail.im.botapi;

import ru.mail.im.botapi.response.GeneralResponse;

import java.io.Reader;

public interface ResponseParser<T extends GeneralResponse> {
    T parse(Reader reader);
}
