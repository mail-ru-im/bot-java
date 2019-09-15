package ru.mail.im.botapi.fetcher;

public class Message {

    private User from;
    private String msgId;
    private String text;
    private long timestamp;

    public User getFrom() {
        return from;
    }

    public String getMsgId() {
        return msgId;
    }

    public String getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
