package ru.mail.im.botapi.api.entity;

public class EventRequest {
    private long lastEventId;
    private int pollTime;

    public long getLastEventId() {
        return lastEventId;
    }

    public EventRequest setLastEventId(final long lastEventId) {
        this.lastEventId = lastEventId;
        return this;
    }

    public int getPollTime() {
        return pollTime;
    }

    public EventRequest setPollTime(final int pollTime) {
        this.pollTime = pollTime;
        return this;
    }
}
