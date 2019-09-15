package ru.mail.im.botapi.fetcher.event.parts;

public class Mention implements Part {

    private String userId;
    private String firstName;
    private String lastName;

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
