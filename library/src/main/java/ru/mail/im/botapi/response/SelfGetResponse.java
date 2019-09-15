package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class SelfGetResponse extends ApiResponse {

    @SerializedName("userId")
    private String userId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("nick")
    private String nick;

    @SerializedName("about")
    private String about;

    @SerializedName("photo")
    private List<String> photo = Collections.emptyList();

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNick() {
        return nick;
    }

    public String getAbout() {
        return about;
    }

    public List<String> getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "SelfGetResponse{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nick='" + nick + '\'' +
                ", about='" + about + '\'' +
                ", photo=" + photo +
                '}';
    }
}
