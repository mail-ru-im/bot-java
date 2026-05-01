package ru.mail.im.botapi.api;

import ru.mail.im.botapi.api.entity.ChatMember;
import ru.mail.im.botapi.entity.ChatAction;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.ChatsGetAdminsResponse;
import ru.mail.im.botapi.response.ChatsGetMembersResponse;
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
            @RequestParam("image") final File avatar
    ) throws IOException;

    @GetRequest("chats/sendActions")
    ApiResponse sendActions(@RequestParam("chatId") final String chatId,
                            @RequestParam("actions") final ChatAction... actions) throws IOException;

    @GetRequest("chats/getInfo")
    ChatsGetInfoResponse getInfo(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("chats/getAdmins")
    ChatsGetAdminsResponse getAdmins(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("chats/getMembers")
    ChatsGetMembersResponse getMembers(@RequestParam("chatId") final String chatId,
                                       @RequestParam("cursor") final String cursor) throws IOException;

    @GetRequest("/chats/getBlockedUsers")
    ChatsUsersResponse getBlockedUsers(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("/chats/getPendingUsers")
    ChatsUsersResponse getPendingUsers(@RequestParam("chatId") final String chatId) throws IOException;

    @GetRequest("/chats/blockUser")
    ApiResponse blockUser(@RequestParam("chatId") final String chatId,
                          @RequestParam("userId") final String userId,
                          @RequestParam("delLastMessages") boolean delLastMessages) throws IOException;

    @GetRequest("/chats/unblockUser")
    ApiResponse unblockUser(@RequestParam("chatId") final String chatId,
                            @RequestParam("userId") final String userId) throws IOException;

    @GetRequest("/chats/resolvePending")
    ApiResponse resolvePending(@RequestParam("chatId") final String chatId,
                               @RequestParam("userId") final String userId,
                               @RequestParam("approve") final Boolean approve) throws IOException;

    @GetRequest("/chats/resolvePending")
    ApiResponse resolvePending(@RequestParam("chatId") final String chatId,
                               @RequestParam("everyone") final Boolean everyone) throws IOException;

    @GetRequest("/chats/setTitle")
    ApiResponse setTitle(@RequestParam("chatId") final String chatId,
                         @RequestParam("title") final String title) throws IOException;

    @GetRequest("/chats/setAbout")
    ApiResponse setAbout(@RequestParam("chatId") final String chatId,
                         @RequestParam("about") final String about) throws IOException;

    @GetRequest("/chats/setRules")
    ApiResponse setRules(@RequestParam("chatId") final String chatId,
                         @RequestParam("rules") final String rules) throws IOException;

    @GetRequest("/chats/pinMessage")
    ApiResponse pinMessage(@RequestParam("chatId") final String chatId,
                           @RequestParam("msgId") final long msgId) throws IOException;

    @GetRequest("/chats/unpinMessage")
    ApiResponse unpinMessage(@RequestParam("chatId") final String chatId,
                             @RequestParam("msgId") final long msgId) throws IOException;
}
