package ru.mail.im.botapi;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.request.ApiRequest;
import ru.mail.im.botapi.request.GetRequest;
import ru.mail.im.botapi.request.PostRequest;
import ru.mail.im.botapi.response.ApiResponse;

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

    public <Req extends ApiRequest<Resp>, Resp extends ApiResponse> Resp execute(final Req apiRequest) throws IOException {
        final Request httpRequest;
        if (apiRequest instanceof GetRequest<?>) {
            httpRequest = buildGetRequest((GetRequest<?>) apiRequest);
        } else if (apiRequest instanceof PostRequest<?>) {
            httpRequest = buildPostRequest((PostRequest<?>) apiRequest);
        } else {
            throw new IllegalArgumentException("Request must be either POST or GET");
        }

        try (Response httpResponse = httpClient.newCall(httpRequest).execute()) {
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

    private Request buildGetRequest(final GetRequest<?> apiRequest) {
        final HttpUrl.Builder urlBuilder = baseUrl.newBuilder()
                .addPathSegment(apiRequest.getName())
                .addQueryParameter("token", token);
        apiRequest.fillQueryString(urlBuilder);
        return new Request.Builder()
                .url(urlBuilder.build())
                .build();
    }

    private Request buildPostRequest(final PostRequest<?> apiRequest) throws IOException {
        final HttpUrl url = baseUrl.newBuilder()
                .addPathSegment(apiRequest.getName())
                .build();
        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", token);
        apiRequest.fillBody(builder);
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }

    private void startInternal() {
        httpClient = new OkHttpClient();
    }

    private void stopInternal() {
        // TODO
    }

}
