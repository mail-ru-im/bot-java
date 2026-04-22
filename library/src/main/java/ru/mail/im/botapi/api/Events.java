package ru.mail.im.botapi.api;

import ru.mail.im.botapi.fetcher.FetchResponse;
import java.io.IOException;

public interface Events {

    @GetRequest("events/get")
    FetchResponse getEvents(
            @RequestParam("lastEventId") final long lastEventId,
            @RequestParam("pollTime") final int pollTime
    ) throws IOException;
}
