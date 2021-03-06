package ru.mail.im.botapi.api;

import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.ChatsGetAdminsResponse;
import ru.mail.im.botapi.response.ChatsGetInfoResponse;

import java.io.IOException;

public interface Chats {

    @GetRequest("chats/sendActions")
    ApiResponse sendActions(@RequestParam("chatId") final String chatId,
                            @RequestParam("actions") final ChatAction... actions) throws IOException;

    @GetRequest("chats/getInfo")
    ChatsGetInfoResponse getInfo(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("chats/getAdmins")
    ChatsGetAdminsResponse getAdmins(@RequestParam("chatId") final String chatId) throws IOException;
}
