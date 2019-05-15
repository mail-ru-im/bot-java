package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

// TODO add to ProGuard
public class ApiResponse {

    @SerializedName("ok")
    private boolean ok;

    // optional, only if "ok" = false
    @SerializedName("description")
    private String description;
}
