package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

// TODO all fields in all GSON-parcelable classes are annotated with @SerializedName. Check that ProGuard keep that annotations.
public class ApiResponse {

    @SerializedName("ok")
    private boolean ok;

    // optional, only if "ok" = false
    @SerializedName("description")
    private String description;

    public boolean isOk() {
        return ok;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "ok=" + ok +
                ", description='" + description + '\'' +
                '}';
    }
}
