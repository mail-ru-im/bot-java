package ru.mail.im.botapi.entity;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }
}
