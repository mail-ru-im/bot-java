package ru.mail.im.botapi.entity;

import com.google.gson.annotations.SerializedName;

public class Buddy {

    @SerializedName("aimId")
    private String id;

    @SerializedName("friendly")
    private String name;

    // TODO maybe enum?
    @SerializedName("userType")
    private String userType;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserType() {
        return userType;
    }
}
