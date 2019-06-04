package ru.mail.im.botapi.sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mail.im.botapi.fetcher.event.Event;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

class FileFetchWriter implements Closeable {

    private final PrintStream stream;

    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .create();

    FileFetchWriter(final File file) throws FileNotFoundException {
        stream = new PrintStream(file);
    }

    void write(final List<Event> events) {
        for (Event<?> event : events) {
            stream.println(gson.toJson(event));
        }
    }

    @Override
    public void close() {
        stream.close();
    }
}
