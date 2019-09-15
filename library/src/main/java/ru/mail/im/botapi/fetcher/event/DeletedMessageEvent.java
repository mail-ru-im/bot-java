package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;

public class DeletedMessageEvent extends Event<DeletedMessageEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitDeletedMessage(this, in);
    }

    public long getMessageId() {
        return eventData.msgId;
    }

    public long getTimestamp() {
        return eventData.timestamp;
    }

    public Chat getChat() {
        return eventData.chat;
    }

    static class Data {
        private long msgId;
        private long timestamp;
        private Chat chat;
    }
}
