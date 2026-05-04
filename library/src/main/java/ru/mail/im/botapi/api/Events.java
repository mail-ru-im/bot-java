package ru.mail.im.botapi.api;

import ru.mail.im.botapi.fetcher.FetchResponse;
import ru.mail.im.botapi.response.ApiResponse;

import java.io.IOException;

public interface Events {

    /**
     * @deprecated Use {@link Events#getEvents(long, int)}
     */
    @Deprecated
    @GetRequest("events/get")
    ApiResponse fetchEvents() throws IOException;

    @GetRequest("events/get")
    FetchResponse getEvents(
            @RequestParam("lastEventId") final long lastEventId,
            @RequestParam("pollTime") final int pollTime
    ) throws IOException;
}
