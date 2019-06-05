package ru.mail.im.botapi.fetcher.event;

public interface EventVisitor<IN, OUT> {

    OUT visitIm(ImEvent event, IN in);

    OUT visitUnknown(UnsupportedEvent event, IN in);

    OUT visitMyInfo(MyInfoEvent event, IN in);

    OUT visitTyping(TypingEvent event, IN in);
}
