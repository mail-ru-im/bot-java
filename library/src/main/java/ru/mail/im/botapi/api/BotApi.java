package ru.mail.im.botapi.api;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;

import javax.annotation.Nonnull;

public class BotApi implements Api {

    private final Messages messages;
    private final Self self;
    private final Chats chats;

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
}
