package ru.mail.im.botapi;

import ru.mail.im.botapi.response.SelfGetResponse;

import java.io.IOException;

public interface Self {

    @GetRequest("self/get")
    SelfGetResponse get() throws IOException;
}
