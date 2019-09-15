package ru.mail.im.botapi.api;

import ru.mail.im.botapi.response.ApiResponse;

import java.io.IOException;

public interface Events {

    @GetRequest("events/get")
    ApiResponse fetchEvents() throws IOException;
}
