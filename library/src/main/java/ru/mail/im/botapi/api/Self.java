package ru.mail.im.botapi.api;

import ru.mail.im.botapi.response.SelfGetResponse;

import java.io.IOException;

public interface Self {

    @GetRequest("self/get")
    SelfGetResponse get() throws IOException;
}
