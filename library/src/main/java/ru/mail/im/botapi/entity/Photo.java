package ru.mail.im.botapi.entity;

import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }
}
