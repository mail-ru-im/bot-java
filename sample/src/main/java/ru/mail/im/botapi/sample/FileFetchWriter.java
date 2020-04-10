package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.fetcher.event.*;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

class FileFetchWriter implements Closeable {

    private final PrintStream stream;

    private final EventVisitor<PrintStream, Void> visitor = new EventVisitor<PrintStream, Void>() {

        @Override
        public Void visitUnknown(final UnknownEvent event, final PrintStream stream) {
            stream.println("# UNKNOWN EVENT");
            stream.format("JSON: %s%n", event.getJson());
            return null;
        }

        @Override
        public Void visitNewMessage(NewMessageEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("From :%s%n", event.getFrom().getNick());
            stream.format("Text :%s%n", event.getText());
            stream.format("Time :%s%n", event.getTimestamp());
            return null;
        }

        @Override
        public Void visitNewChatMembers(NewChatMembersEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("From :%s%n", event.getAddedBy().getNick());
            stream.format("New members :%s%n", event.getMembers());
            return null;
        }

        @Override
        public Void visitLeftChatMembers(LeftChatMembersEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("From :%s%n", event.getRemovedBy().getNick());
            stream.format("Left members :%s%n", event.getMembers());
            return null;
        }

        @Override
        public Void visitDeletedMessage(DeletedMessageEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("MsgId :%s%n", event.getMessageId());
            stream.format("Timestamp :%s%n", event.getTimestamp());
            return null;
        }

        @Override
        public Void visitEditedMessage(EditedMessageEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("Text :%s%n", event.getText());
            stream.format("Timestamp :%s%n", event.getTimestamp());
            stream.format("Edited timestamp :%s%n", event.getEditedTimestamp());
            stream.format("User :%s%n", event.getFrom().getNick());
            return null;
        }

        @Override
        public Void visitPinnedMessage(PinnedMessageEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("Text :%s%n", event.getText());
            stream.format("Timestamp :%s%n", event.getTimestamp());
            stream.format("User :%s%n", event.getFrom().getNick());
            return null;
        }

        @Override
        public Void visitUnpinnedMessage(UnpinnedMessageEvent event, PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Chat :%s%n", event.getChat().getChatId());
            stream.format("Timestamp :%s%n", event.getTimestamp());
            return null;
        }

        @Override
        public Void visitCallbackQuery(final CallbackQueryEvent event, final PrintStream printStream) {
            stream.format("Event type :%s%n", event.getType());
            stream.format("Message chat :%s%n", event.getMessageChat().getChatId());
            stream.format("Message timestamp :%s%n", event.getMessageTimestamp());
            stream.format("CallbackData :%s%n", event.getCallbackData());
            stream.format("Callback query id :%s%n", event.getQueryId());
            return null;
        }
    };

    FileFetchWriter(final File file) throws FileNotFoundException {
        stream = new PrintStream(file);
    }

    void write(final List<Event> events) {
        for (Event<?> event : events) {
            event.accept(visitor, stream);
            stream.println();
        }
    }

    @Override
    public void close() {
        stream.close();
    }
}
