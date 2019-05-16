package ru.mail.im.botapi;

import javax.annotation.Nullable;

class QueryParameter {
    final String name;
    final Object value;

    QueryParameter(final String name, final Object value) {
        this.name = name;
        this.value = value;
    }

    @Nullable
    String getValueAsString() {
        return value == null ? null : value.toString();
    }
}
