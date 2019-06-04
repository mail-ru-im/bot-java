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
import ru.mail.im.botapi.entity.ChatType;
import ru.mail.im.botapi.json.ChatTypeGsonDeserializer;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.List;

class Api {

    private final OkHttpClient httpClient;
    private final HttpUrl baseUrl;
    private final String token;

    private final Gson gson;
    private final Messages messages;
    private final Self self;
    private final Chats chats;

    Api(@Nonnull final OkHttpClient httpClient, @Nonnull String baseUrl, @Nonnull final String token) {
        this.httpClient = httpClient;
        this.baseUrl = HttpUrl.get(baseUrl);
        this.token = token;

        gson = new GsonBuilder()
                .registerTypeAdapter(ChatType.class, new ChatTypeGsonDeserializer())
                .create();
        messages = createImplementation(Messages.class);
        self = createImplementation(Self.class);
        chats = createImplementation(Chats.class);
    }

    Self self() {
        return self;
    }

    Messages messages() {
        return messages;
    }

    Chats chats() {
        return chats;
    }

    private <T> T createImplementation(final Class<T> clazz) {
        Object impl = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            final List<QueryParameter> query = new QueryBuilder(method, args).build();
            final Request request = buildRequest(method, query);
            return execute(request, method.getReturnType());
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

    private Request buildRequest(final Method method, final List<QueryParameter> query) throws IOException {
        verifyMethod(method);
        GetRequest getAnnotation = method.getAnnotation(GetRequest.class);
        if (getAnnotation != null) {
            return buildGetRequest(getAnnotation.value(), query);
        }

        PostRequest postAnnotation = method.getAnnotation(PostRequest.class);
        if (postAnnotation != null) {
            return buildPostRequest(postAnnotation.value(), query);
        }
        throw new IllegalArgumentException("Request must be GET or POST");
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
            if (parameter.value instanceof File) {
                File file = (File) parameter.value;
                builder.addFormDataPart(parameter.name, file.getName(), RequestBody.create(null, Files.readAllBytes(file.toPath())));
            } else if (parameter.value != null) {
                builder.addFormDataPart(parameter.name, parameter.value.toString());
            }
        }
        return new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
    }

    private static void verifyMethod(final Method method) {
        for (Class<?> exClass : method.getExceptionTypes()) {
            if (exClass == IOException.class) {
                return;
            }
        }
        throw new RuntimeException("Request method must throw IOException");
    }
}
