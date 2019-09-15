package ru.mail.im.botapi.fetcher.event.parts;

import ru.mail.im.botapi.fetcher.Message;

public class Reply implements Part {
    private Message message;

    public Message getMessage() {
        return message;
    }
}
