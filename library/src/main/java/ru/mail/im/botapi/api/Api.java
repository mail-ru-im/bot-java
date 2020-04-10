package ru.mail.im.botapi.api;

public interface Api {
    Self self();
    Messages messages();
    Chats chats();
}
