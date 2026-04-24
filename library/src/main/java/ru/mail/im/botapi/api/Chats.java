package ru.mail.im.botapi.api;

import ru.mail.im.botapi.api.entity.ChatMember;
import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.ChatsGetAdminsResponse;
import ru.mail.im.botapi.response.ChatsUsersResponse;
import ru.mail.im.botapi.response.ChatsGetInfoResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Chats {

    @GetRequest("/chats/members/delete")
    ApiResponse delete(
            @RequestParam("chatId") final String chatId,
            @RequestParam("members") final List<ChatMember> members
    ) throws IOException;

    @PostRequest("/chats/avatar/set")
    ApiResponse setAvatar(
            @RequestParam("chatId") final String chatId,
            @RequestParam("members") final File avatar
    ) throws IOException;

    @GetRequest("chats/sendActions")
    ApiResponse sendActions(@RequestParam("chatId") final String chatId,
                            @RequestParam("actions") final ChatAction... actions) throws IOException;

    @GetRequest("chats/getInfo")
    ChatsGetInfoResponse getInfo(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("chats/getAdmins")
    ChatsGetAdminsResponse getAdmins(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("chats/getMembers")
    ChatsGetAdminsResponse getMembers(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("/chats/getBlockedUsers")
    ChatsUsersResponse getBlockedUsers(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("/chats/getPendingUsers")
    ChatsUsersResponse getPendingUsers(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("/chats/blockUser")
    ApiResponse blockUser(@RequestParam("chatId") final String chatId,
                          @RequestParam("userId") final String userId) throws IOException;

    @GetRequest("/chats/unblockUser")
    ApiResponse unblockUser(@RequestParam("chatId") final String chatId,
                          @RequestParam("userId") final String userId) throws IOException;
}
