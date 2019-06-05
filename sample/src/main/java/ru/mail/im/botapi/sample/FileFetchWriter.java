package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.fetcher.event.EventVisitor;
import ru.mail.im.botapi.fetcher.event.ImEvent;
import ru.mail.im.botapi.fetcher.event.MyInfoEvent;
import ru.mail.im.botapi.fetcher.event.TypingEvent;
import ru.mail.im.botapi.fetcher.event.UnsupportedEvent;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

class FileFetchWriter implements Closeable {

    private final PrintStream stream;

    private final EventVisitor<PrintStream, Void> visitor = new EventVisitor<PrintStream, Void>() {

        @Override
        public Void visitIm(final ImEvent event, final PrintStream stream) {
            stream.format("# INPUT MESSAGE [%d]%n", event.getSeqNum());
            stream.format("From: %s [%s]%n", event.getSenderName(), event.getSenderId());
            stream.format("Text: %s%n", event.getText());
            return null;
        }

        @Override
        public Void visitUnknown(final UnsupportedEvent event, final PrintStream stream) {
            stream.println("# UNKNOWN EVENT");
            stream.format("Type: %s%n", event.getType());
            return null;
        }

        @Override
        public Void visitMyInfo(final MyInfoEvent event, final PrintStream stream) {
            stream.format("# MY INFO [%d]%n", event.getSeqNum());
            stream.format("SN  : %s%n", event.getId());
            stream.format("Name: %s%n", event.getName());
            stream.format("Nick: %s%n", event.getNick());
            return null;
        }

        @Override
        public Void visitTyping(final TypingEvent event, final PrintStream stream) {
            stream.format("# TYPING [%d]%n", event.getSeqNum());
            stream.format("Who   : %s%n", event.getTypistId());
            stream.format("Status: %s%n", event.getStatus());
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
