package ru.mail.im.botapi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.BotLogger;
import ru.mail.im.botapi.entity.ChatType;
import ru.mail.im.botapi.json.ChatTypeGsonDeserializer;

import java.io.IOException;

class OkHttpRequestExecutor implements RequestExecutor {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ChatType.class, new ChatTypeGsonDeserializer())
            .create();

    private OkHttpClient httpClient;

    OkHttpRequestExecutor(final OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public <T> T execute(final Request request, final Class<T> responseClass) throws IOException {
        BotLogger.i("request:" + request.url());
        try (Response response = httpClient.newCall(request).execute()) {
            String message = response.message();
            BotLogger.i("response message:" + message);
            if (!response.isSuccessful()) {
                throw new IOException("Bad HTTP status " + response.code());
            }
            final ResponseBody body = response.body();
            if (body == null) {
                throw new NullPointerException("Response body is null");
            }
            String json = body.string() ;
            BotLogger.i("response body:" + json);
            return gson.fromJson(json, responseClass);
        }
    }
}
