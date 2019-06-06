package ru.mail.im.botapi.api;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.util.List;

class ApiImplementationFactory {

    private final RequestExecutor requestExecutor;
    private final HttpUrl baseUrl;
    private final String token;

    ApiImplementationFactory(@Nonnull final RequestExecutor requestExecutor,
                             @Nonnull final String baseUrl,
                             @Nonnull final String token) {
        this.requestExecutor = requestExecutor;
        this.baseUrl = HttpUrl.get(baseUrl);
        this.token = token;
    }

    <T> T createImplementation(final Class<T> clazz) {
        Object impl = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            final List<QueryParameter> query = new QueryBuilder(method, args).build();
            final Request request = buildRequest(method, query);
            return requestExecutor.execute(request, method.getReturnType());
        });
        return clazz.cast(impl);
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
                .addPathSegments(name)
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
                .addPathSegments(name)
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
