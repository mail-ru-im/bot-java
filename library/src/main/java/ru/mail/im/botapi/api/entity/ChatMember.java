package ru.mail.im.botapi.api.entity;

public class ChatMember {

    private static final String SN = "sn";
    private final String chatSn;
    private final String memberId;

    private ChatMember(final String chatSn, final String memberId) {
        this.chatSn = chatSn;
        this.memberId = memberId;
    }

    public static ChatMember createChatMember(final String memberId) {
        return new ChatMember(SN, memberId);
    }

    public String getChatSn() {
        return chatSn;
    }

    public String getMemberId() {
        return memberId;
    }
}
