package ru.mail.im.botapi.fetcher.event;

public class UnsupportedEvent extends Event<Void> {

    private transient String type;

    public UnsupportedEvent(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitUnknown(this, in);
    }
}
