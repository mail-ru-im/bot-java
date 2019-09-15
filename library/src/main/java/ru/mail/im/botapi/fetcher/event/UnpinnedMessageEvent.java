package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;

public class UnpinnedMessageEvent extends Event<UnpinnedMessageEvent.Data> {
    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitUnpinnedMessage(this, in);
    }

    public long getMsgId() {
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