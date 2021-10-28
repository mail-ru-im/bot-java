package ru.mail.im.botapi.api.entity;

public class InlineKeyboardButton {

    private final String text;
    private final String url;
    private final String callbackData;
    private final String style;

    private InlineKeyboardButton(final String text, final String url, final String callbackData, final String style) {
        this.text = text;
        this.url = url;
        this.callbackData = callbackData;
        this.style = style;
    }

    public static InlineKeyboardButton callbackButton(final String text, final String callbackData, final String style) {
        return new InlineKeyboardButton(text, null, callbackData, style);
    }

    public static InlineKeyboardButton urlButton(final String text, final String url, final String style) {
        return new InlineKeyboardButton(text, url, null, style);
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

    public String getStyle() {
        return style;
    }
}
