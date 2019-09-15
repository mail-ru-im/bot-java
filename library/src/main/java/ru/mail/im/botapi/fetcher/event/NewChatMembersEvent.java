package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;
import ru.mail.im.botapi.fetcher.User;

import java.util.List;

public class NewChatMembersEvent extends Event<NewChatMembersEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitNewChatMembers(this, in);
    }

    public Chat getChat() {
        return eventData.chat;
    }

    public List<User> getMembers() {
        return eventData.newMembers;
    }

    public User getAddedBy() {
        return eventData.addedBy;
    }

    static class Data {
        private Chat chat;
        private List<User> newMembers;
        private User addedBy;
    }
}
