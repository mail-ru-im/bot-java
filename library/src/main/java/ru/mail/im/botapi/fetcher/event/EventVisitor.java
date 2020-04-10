package ru.mail.im.botapi.fetcher.event;

public interface EventVisitor<IN, OUT> {

    OUT visitUnknown(UnknownEvent event, IN in);

    OUT visitNewMessage(NewMessageEvent event, IN in);

    OUT visitNewChatMembers(NewChatMembersEvent event, IN in);

    OUT visitLeftChatMembers(LeftChatMembersEvent leftChatMembersEvent, IN in);

    OUT visitDeletedMessage(DeletedMessageEvent deletedMessageEvent, IN in);

    OUT visitEditedMessage(EditedMessageEvent editedMessageEvent, IN in);

    OUT visitPinnedMessage(PinnedMessageEvent pinnedMessageEvent, IN in);

    OUT visitUnpinnedMessage(UnpinnedMessageEvent unpinnedMessageEvent, IN in);

    OUT visitCallbackQuery(CallbackQueryEvent callbackQueryEvent, IN in);
}
