package ru.mail.im.botapi;

import ru.mail.im.botapi.response.ApiResponse;

import java.io.IOException;

public interface Chats {

    @GetRequest("chats/sendActions")
    ApiResponse sendActions(@RequestParam("chatId") final String chatId,
                            @RequestParam("actions") final ChatAction... actions) throws IOException;

}
