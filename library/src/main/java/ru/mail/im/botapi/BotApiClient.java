package ru.mail.im.botapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotApiClient {

    private final HttpUrl baseUrl;
    private final String token;
    private final AtomicBoolean started = new AtomicBoolean();

    private OkHttpClient httpClient;
    private Gson gson;
    private Messages messages;
    private Self self;

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

    public Messages messages() {
        return messages;
    }

    public Self self() {
        return self;
    }

    private void startInternal() {
        httpClient = new OkHttpClient();
        gson = new GsonBuilder().create();
        messages = createImplementation(Messages.class);
        self = createImplementation(Self.class);
    }

    private void stopInternal() {
        // TODO
    }

    private <T> T createImplementation(final Class<T> clazz) {
        Object impl = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            GetRequest getAnnotation = method.getAnnotation(GetRequest.class);
            if (getAnnotation != null) {
                final Request request = buildGetRequest(getAnnotation.value(), buildQuery(method, args));
                return execute(request, method.getReturnType());
            }
            PostRequest postAnnotation = method.getAnnotation(PostRequest.class);
            if (postAnnotation != null) {
                final Request request = buildPostRequest(postAnnotation.value(), buildQuery(method, args));
                return execute(request, method.getReturnType());
            }
            throw new IllegalArgumentException("Request must be GET or POST");
        });
        return clazz.cast(impl);
    }

    private Object execute(final Request request, final Class<?> responseClass) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Bad HTTP status " + response.code());
            }
            final ResponseBody body = response.body();
            if (body == null) {
                throw new NullPointerException("Response body is null");
            }
            return gson.fromJson(body.charStream(), responseClass);
        }
    }

    private Request buildGetRequest(final String name, final List<QueryParameter> query) {
        final HttpUrl.Builder urlBuilder = baseUrl.newBuilder()
                .addPathSegment(name)
                .addQueryParameter("token", token);
        for (QueryParameter parameter : query) {
            urlBuilder.addQueryParameter(parameter.name, parameter.getValueAsString());
        }
        return new Request.Builder()
                .url(urlBuilder.build())
                .build();
    }

    private Request buildPostRequest(final String name, final List<QueryParameter> query) throws IOException {
        final HttpUrl url = baseUrl.newBuilder()
                .addPathSegment(name)
                .build();
        final MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", token);
        for (QueryParameter parameter : query) {
            if (parameter.value != null) {
                if (parameter.value instanceof File) {
                    File file = (File) parameter.value;
                    builder.addFormDataPart(parameter.name, file.getName(), RequestBody.create(null, Files.readAllBytes(file.toPath())));
                } else {
                    builder.addFormDataPart(parameter.name, parameter.value.toString());
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }

    private static List<QueryParameter> buildQuery(final Method method, final Object[] args) {
        if (args == null) {
            return Collections.emptyList();
        }
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        final List<QueryParameter> result = new ArrayList<>(args.length);
        for (int i = 0; i < args.length; i++) {
            boolean found = false;
            for (Annotation annotation : paramAnnotations[i]) {
                if (annotation instanceof RequestParam) {
                    final String name = ((RequestParam) annotation).value();
                    if (args[i].getClass().isArray()) {
                        int length = Array.getLength(args[i]);
                        for (int j = 0; j < length; j++) {
                            result.add(new QueryParameter(name, Array.get(args[i], j)));
                        }
                    } else {
                        result.add(new QueryParameter(name, args[i]));
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Parameter with index " + i + " was not annotated with @RequestParam");
            }
        }
        return result;
    }
}
