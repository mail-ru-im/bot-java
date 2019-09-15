package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;
import ru.mail.im.botapi.fetcher.User;
import ru.mail.im.botapi.fetcher.event.parts.Part;

import java.util.List;

public class NewMessageEvent extends Event<NewMessageEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitNewMessage(this, in);
    }

    public long getMessageId() {
        return withData(data -> data.msgId);
    }

    public long getTimestamp() {
        return withData(data -> data.timestamp);
    }

    public String getText() {
        return withData(data -> data.text);
    }

    public User getFrom() {
        return withData(data -> data.from);
    }

    public Chat getChat() {
        return withData(data -> data.chat);
    }

    public List<Part> getParts() {
        return withData(data -> data.parts);
    }

    static class Data {
        private long msgId;
        private long timestamp;
        private String text;
        private Chat chat;
        private User from;
        private List<Part> parts;
    }
}
