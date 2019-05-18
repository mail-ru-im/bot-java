package ru.mail.im.botapi.entity;

import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("userId")
    private String userId;

    @SerializedName("creator")
    private boolean creator;

    public String getUserId() {
        return userId;
    }

    public boolean isCreator() {
        return creator;
    }
}
