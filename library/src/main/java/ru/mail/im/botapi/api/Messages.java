package ru.mail.im.botapi.api;

import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.MessageResponse;

import java.io.File;
import java.io.IOException;

public interface Messages {

    @GetRequest("messages/sendText")
    MessageResponse sendText(@RequestParam("chatId") final String chatId,
                             @RequestParam("text") final String text) throws IOException;

    @GetRequest("messages/sendText")
    MessageResponse replyText(@RequestParam("chatId") final String chatId,
                              @RequestParam("text") final String text,
                              @RequestParam("replyMsgId") final long replyMsgId) throws IOException;

    @GetRequest("messages/sendText")
    MessageResponse forwardText(@RequestParam("chatId") final String chatId,
                                @RequestParam("text") final String text,
                                @RequestParam("forwardChatId") final String forwardChatId,
                                @RequestParam("forwardMsgId") final long forwardMsgId) throws IOException;


    @PostRequest("messages/sendFile")
    MessageResponse replyFile(@RequestParam("chatId") final String chatId,
                                  @RequestParam("file") final File file,
                                  @RequestParam("replyMsgId") final long replyMsgId,
                                  @RequestParam("caption") String caption) throws IOException;

    @PostRequest("messages/sendFile")
    MessageResponse forwardFile(@RequestParam("chatId") final String chatId,
                                    @RequestParam("file") final File file,
                                    @RequestParam("forwardChatId") final String forwardChatId,
                                    @RequestParam("forwardMsgId") final long forwardMsgId,
                                    @RequestParam("caption") String caption) throws IOException;

    @PostRequest("messages/sendFile")
    MessageResponse sendFile(@RequestParam("chatId") final String chatId,
                             @RequestParam("file") final File file,
                             @RequestParam("caption") String caption) throws IOException;

    @GetRequest("messages/editText")
    ApiResponse editText(@RequestParam("chatId") final String chatId,
                         @RequestParam("msgId") final long msgId,
                         @RequestParam("text") final String newText) throws IOException;


    @GetRequest("messages/deleteMessages")
    ApiResponse deleteMessages(@RequestParam("chatId") final String chatId,
                               @RequestParam("msgId") final long... msgId) throws IOException;

}
