package ru.mail.im.botapi.api;

import okhttp3.Request;

import java.io.IOException;

interface RequestExecutor {
    <T> T execute(Request request, Class<T> responseClass) throws IOException;
}
