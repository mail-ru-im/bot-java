package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.entity.Admin;

import java.util.Collections;
import java.util.List;

public class ChatsGetMembersResponse extends ApiResponse {

    @SerializedName("members")
    private List<Admin> members = Collections.emptyList();

    public List<Admin> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        return "ChatsGetMembersResponse{" +
                "members=" + members +
                '}';
    }
}
