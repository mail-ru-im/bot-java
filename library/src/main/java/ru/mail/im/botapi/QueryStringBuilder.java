package ru.mail.im.botapi;

import javax.annotation.Nullable;

public interface QueryStringBuilder {
    void addQueryParameter(final String name, @Nullable final Object value);
}
