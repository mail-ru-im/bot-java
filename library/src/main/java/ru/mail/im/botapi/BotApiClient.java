package ru.mail.im.botapi;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.request.ApiRequest;
import ru.mail.im.botapi.request.GetRequest;
import ru.mail.im.botapi.request.PostRequest;
import ru.mail.im.botapi.response.ApiResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
        apiRequest.buildQueryString(new OkHttpQueryStringBuilder(urlBuilder));
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
        apiRequest.buildBody(new OkHttpMultipartFormDataBuilder(builder));
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

    private static class OkHttpQueryStringBuilder implements QueryStringBuilder {

        private final HttpUrl.Builder urlBuilder;

        private OkHttpQueryStringBuilder(final HttpUrl.Builder urlBuilder) {
            this.urlBuilder = urlBuilder;
        }

        @Override
        public void addQueryParameter(final String name, @Nullable final Object value) {
            urlBuilder.addQueryParameter(name, value == null ? null : value.toString());
        }
    }

    private static class OkHttpMultipartFormDataBuilder implements MultipartFormDataBuilder {

        private final MultipartBody.Builder builder;

        private OkHttpMultipartFormDataBuilder(final MultipartBody.Builder builder) {
            this.builder = builder;
        }

        @Override
        public void addPart(final String name, @Nullable final Object value) {
            if (value != null) {
                builder.addFormDataPart(name, value.toString());
            }
        }

        @Override
        public void addPart(final String name, final String filename, final byte[] content) {
            builder.addFormDataPart(name, filename, RequestBody.create(null, content));
        }
    }
}
