package ru.mail.im.botapi;

public enum ChatAction {
    LOOKING("looking"),
    TYPING("typing"),
    NONE("");

    private String value;

    ChatAction(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
