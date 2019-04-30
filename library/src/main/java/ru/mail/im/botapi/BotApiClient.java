package ru.mail.im.botapi;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.request.GeneralRequest;
import ru.mail.im.botapi.response.GeneralResponse;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotApiClient {

    private final HttpUrl baseUrl;
    private final String token;
    private final AtomicBoolean started = new AtomicBoolean();

    private OkHttpClient httpClient;

    public BotApiClient(@Nonnull String baseUrl, @Nonnull final String token) {
        this.baseUrl = HttpUrl.get(baseUrl);
        this.token = token;
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            startInternal();
        }
    }

    public void stop() {
        if (started.compareAndSet(true, false)) {
            stopInternal();
        }
    }

    public <Req extends GeneralRequest<Resp>, Resp extends GeneralResponse> Resp execute(final Req apiRequest) throws IOException {
        final HttpUrl.Builder urlBuilder = baseUrl.newBuilder()
                .addQueryParameter("token", token);

        apiRequest.buildUrl(urlBuilder);

        final Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build());

        final RequestBody requestBody = apiRequest.buildBody();
        if (requestBody != null) {
            requestBuilder.post(requestBody);
        }

        try (Response httpResponse = httpClient.newCall(requestBuilder.build()).execute()) {
            if (!httpResponse.isSuccessful()) {
                throw new IOException("Bad HTTP status " + httpResponse.code());
            }
            final ResponseBody body = httpResponse.body();
            if (body == null) {
                throw new NullPointerException("Response body is null");
            }
            return apiRequest.getResponseParser().parse(body.charStream());
        }
    }

    private void startInternal() {
        httpClient = new OkHttpClient();
    }

    private void stopInternal() {
        // TODO
    }

}
