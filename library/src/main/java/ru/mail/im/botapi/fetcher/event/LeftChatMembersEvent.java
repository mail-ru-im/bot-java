package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;
import ru.mail.im.botapi.fetcher.User;

import java.util.List;

public class LeftChatMembersEvent extends Event<LeftChatMembersEvent.Data> {
    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitLeftChatMembers(this, in);
    }

    public Chat getChat() {
        return eventData.chat;
    }

    public List<User> getMembers() {
        return eventData.leftMembers;
    }

    public User getRemovedBy() {
        return eventData.removedBy;
    }

    static class Data {
        private Chat chat;
        private List<User> leftMembers;
        private User removedBy;
    }
}
