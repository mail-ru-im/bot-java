package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.entity.Admin;

import java.util.Collections;
import java.util.List;

public class ChatsGetAdminsResponse extends ApiResponse {

    @SerializedName("admins")
    private List<Admin> admins = Collections.emptyList();

    public List<Admin> getAdmins() {
        return admins;
    }

    @Override
    public String toString() {
        return "ChatsGetAdminsResponse{" +
                "admins=" + admins +
                '}';
    }
}
