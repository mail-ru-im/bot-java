package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.entity.User;
import java.util.Collections;
import java.util.List;

public class ChatsUsersResponse extends ApiResponse {

    @SerializedName("users")
    private List<User> users = Collections.emptyList();

    public List<User> getAdmins() {
        return users;
    }

    @Override
    public String toString() {
        return "ChatsUsersResponse{" +
                "users=" + users +
                '}';
    }
}
