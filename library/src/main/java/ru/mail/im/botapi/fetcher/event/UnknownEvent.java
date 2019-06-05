package ru.mail.im.botapi.fetcher.event;

public class UnknownEvent extends Event<Void> {

    private transient String json;

    public UnknownEvent(final String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitUnknown(this, in);
    }
}
