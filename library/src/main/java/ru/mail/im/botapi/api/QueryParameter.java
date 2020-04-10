package ru.mail.im.botapi.api;

import com.google.gson.Gson;

import javax.annotation.Nullable;

class QueryParameter {
    final Gson gson;
    final String name;
    final Object value;

    QueryParameter(final Gson gson, final String name, final Object value) {
        this.gson = gson;
        this.name = name;
        this.value = value;
    }

    @Nullable
    String getValueAsString() {
        if (value != null) {
            if (value instanceof String) {
                return value.toString();
            } else {
                return gson.toJson(value);
            }
        } else {
            return null;
        }
    }
}
