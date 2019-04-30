package ru.mail.im.botapi;

import javax.annotation.Nullable;

public interface MultipartFormDataBuilder {
    void addPart(String name, @Nullable Object value);

    void addPart(String name, String filename, byte[] content);
}
