package ru.mail.im.botapi.api.entity;

public class InlineKeyboardButton {

    private final String text;
    private final String url;
    private final String callbackData;

    private InlineKeyboardButton(final String text, final String url, final String callbackData) {
        this.text = text;
        this.url = url;
        this.callbackData = callbackData;
    }

    public static InlineKeyboardButton callbackButton(final String text, final String callbackData) {
        return new InlineKeyboardButton(text, null, callbackData);
    }

    public static InlineKeyboardButton urlButton(final String text, final String url) {
        return new InlineKeyboardButton(text, url, null);
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public String getCallbackData() {
        return callbackData;
    }
}
