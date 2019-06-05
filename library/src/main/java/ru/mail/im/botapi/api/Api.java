package ru.mail.im.botapi.api;

import okhttp3.OkHttpClient;

import javax.annotation.Nonnull;

public class Api {

    private final Messages messages;
    private final Self self;
    private final Chats chats;

    public Api(@Nonnull final OkHttpClient httpClient, @Nonnull String baseUrl, @Nonnull final String token) {
        final ApiImplementationFactory factory = new ApiImplementationFactory(
                new OkHttpRequestExecutor(httpClient),
                baseUrl,
                token);

        messages = factory.createImplementation(Messages.class);
        self = factory.createImplementation(Self.class);
        chats = factory.createImplementation(Chats.class);
    }

    public Self self() {
        return self;
    }

    public Messages messages() {
        return messages;
    }

    public Chats chats() {
        return chats;
    }
}
