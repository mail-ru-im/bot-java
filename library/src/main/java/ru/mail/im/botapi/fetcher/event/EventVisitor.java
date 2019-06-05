package ru.mail.im.botapi.fetcher.event;

public interface EventVisitor<IN, OUT> {

    OUT visitIm(ImEvent event, IN in);

    OUT visitUnknown(UnknownEvent event, IN in);

    OUT visitMyInfo(MyInfoEvent event, IN in);

    OUT visitTyping(TypingEvent event, IN in);

    OUT visitBuddyList(BuddyListEvent event, IN in);

    OUT visitService(ServiceEvent event, IN in);
}
