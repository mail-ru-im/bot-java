package ru.mail.im.botapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mail.im.botapi.response.ApiResponse;

import java.io.Reader;

public class GsonResponseParser<T extends ApiResponse> implements ResponseParser<T> {

    private final Class<T> clazz;

    private static final Gson GSON = new GsonBuilder().create();

    public GsonResponseParser(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T parse(final Reader reader) {
        return GSON.fromJson(reader, clazz);
    }
}
