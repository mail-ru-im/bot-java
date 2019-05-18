package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.entity.ChatType;
import ru.mail.im.botapi.entity.Photo;

import java.util.List;

public class ChatsGetInfoResponse extends ApiResponse {

    @SerializedName("type")
    private ChatType type;

    @SerializedName("photo")
    private List<Photo> photos;

    @SerializedName("about")
    private String about;

    /***** For private chats *****/

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("nick")
    private String nick;

    @SerializedName("isBot")
    private boolean isBot;

    @SerializedName("phone")
    private String phone;

    /***** For groups and channels *****/

    @SerializedName("title")
    private String title;

    @SerializedName("inviteLink")
    private String inviteLink;

    @SerializedName("public")
    private boolean isPublic;

    @SerializedName("joinModeration")
    private boolean hasJoinModeration;

    /***** For groups only *****/
    @SerializedName("rules")
    private String rules;

    public ChatType getType() {
        return type;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public String getAbout() {
        return about;
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

    public boolean isBot() {
        return isBot;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public boolean hasJoinModeration() {
        return hasJoinModeration;
    }

    public String getRules() {
        return rules;
    }
}
