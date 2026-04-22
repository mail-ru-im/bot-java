package ru.mail.im.botapi.api;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

import javax.annotation.Nonnull;

public class BotApi implements Api {

    private final Messages messages;
    private final Self self;
    private final Chats chats;
    private final Files files;
    private final Events events;

    public BotApi(
            @Nonnull final Gson gson,
            @Nonnull final OkHttpClient httpClient,
            @Nonnull final String baseUrl,
            @Nonnull final String token
    ) {
        final ApiImplementationFactory factory = new ApiImplementationFactory(
            gson,
            new OkHttpRequestExecutor(httpClient),
            baseUrl,
            token
        );

        messages = factory.createImplementation(Messages.class);
        self = factory.createImplementation(Self.class);
        chats = factory.createImplementation(Chats.class);
        files = factory.createImplementation(Files.class);
        events = factory.createImplementation(Events.class);
    }

    @Override
    public Self self() {
        return self;
    }

    @Override
    public Messages messages() {
        return messages;
    }

    @Override
    public Chats chats() {
        return chats;
    }

    @Override
    public Files files() {
        return files;
    }

    @Override
    public Events events() {
        return events;
    }
}
