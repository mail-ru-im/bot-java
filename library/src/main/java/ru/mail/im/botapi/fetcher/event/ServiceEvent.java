package ru.mail.im.botapi.fetcher.event;

public class ServiceEvent extends Event<Void> {

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitService(this, in);
    }
}
