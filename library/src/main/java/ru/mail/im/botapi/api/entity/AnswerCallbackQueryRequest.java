package ru.mail.im.botapi.api.entity;

public class AnswerCallbackQueryRequest {
    private String queryId;
    private String text;
    private Boolean showAlert;
    private String url;

    public static AnswerCallbackQueryRequest answerText(final String queryId, final String text, final boolean showAlert) {
        return new AnswerCallbackQueryRequest()
            .setQueryId(queryId)
            .setText(text)
            .setShowAlert(showAlert);
    }

    public static AnswerCallbackQueryRequest answerUrl(final String queryId, final String text, final String url) {
        return new AnswerCallbackQueryRequest()
            .setQueryId(queryId)
            .setText(text)
            .setUrl(url);
    }

    public String getQueryId() {
        return queryId;
    }

    public AnswerCallbackQueryRequest setQueryId(final String queryId) {
        this.queryId = queryId;
        return this;
    }

    public String getText() {
        return text;
    }

    public AnswerCallbackQueryRequest setText(final String text) {
        this.text = text;
        return this;
    }

    public Boolean getShowAlert() {
        return showAlert;
    }

    public AnswerCallbackQueryRequest setShowAlert(final Boolean showAlert) {
        this.showAlert = showAlert;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public AnswerCallbackQueryRequest setUrl(final String url) {
        this.url = url;
        return this;
    }
}
