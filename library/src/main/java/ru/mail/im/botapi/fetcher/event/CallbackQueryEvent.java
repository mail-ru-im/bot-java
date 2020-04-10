package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;
import ru.mail.im.botapi.fetcher.User;
import ru.mail.im.botapi.fetcher.event.parts.Part;

import java.util.List;

public class CallbackQueryEvent extends Event<CallbackQueryEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitCallbackQuery(this, in);
    }

    public String getCallbackData() {
        return withData(data -> data.callbackData);
    }

    public String getQueryId() {
        return withData(data -> data.queryId);
    }

    public User getFrom() {
        return withData(data -> data.from);
    }

    public User getMessageFrom() {
        return withData(data -> data.message.from);
    }

    public Chat getMessageChat() {
        return withData(data -> data.message.chat);
    }

    public String getMessageText() {
        return withData(data -> data.message.text);
    }

    public long getMessageId() {
        return withData(data -> data.message.msgId);
    }

    public long getMessageTimestamp() {
        return withData(data -> data.message.timestamp);
    }

    public List<Part> getMessageParts() {
        return withData(data -> data.message.parts);
    }

    static class Data {
        private String callbackData;
        private User from;
        private Message message;
        private String queryId;
    }

    static class Message {
        private Chat chat;
        private User from;
        private String text;
        private long msgId;
        private long timestamp;
        private List<Part> parts;
    }
}
