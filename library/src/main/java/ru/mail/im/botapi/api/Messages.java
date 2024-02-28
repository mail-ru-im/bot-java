package ru.mail.im.botapi.api;

import ru.mail.im.botapi.api.entity.InlineKeyboardButton;
import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.MessageResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Messages {

    @GetRequest("messages/sendText")
    MessageResponse sendText(
        @RequestParam("chatId") final String chatId,
        @RequestParam("text") final String text,
        @RequestParam("format") final String format,
        @RequestParam("parseMode") final String parseMode,
        @RequestParam("replyMsgId") final long[] replyMsgId,
        @RequestParam("forwardChatId") final String forwardChatId,
        @RequestParam("forwardMsgId") final long[] forwardMsgId,
        @RequestParam("inlineKeyboardMarkup") final List<List<InlineKeyboardButton>> keyboard
    ) throws IOException;

    @PostRequest("messages/sendFile")
    MessageResponse sendFile(
        @RequestParam("chatId") final String chatId,
        @RequestParam("file") final File file,
        @RequestParam("caption") String caption,
        @RequestParam("replyMsgId") final long[] replyMsgId,
        @RequestParam("forwardChatId") final String forwardChatId,
        @RequestParam("forwardMsgId") final long[] forwardMsgId,
        @RequestParam("inlineKeyboardMarkup") final List<List<InlineKeyboardButton>> keyboard
    ) throws IOException;

    @GetRequest("messages/editText")
    ApiResponse editText(
        @RequestParam("chatId") final String chatId,
        @RequestParam("msgId") final long msgId,
        @RequestParam("text") final String newText,
        @RequestParam("format") final String format,
        @RequestParam("inlineKeyboardMarkup") final List<List<InlineKeyboardButton>> keyboard
    ) throws IOException;


    @GetRequest("messages/deleteMessages")
    ApiResponse deleteMessages(
        @RequestParam("chatId") final String chatId,
        @RequestParam("msgId") final long[] msgId
    ) throws IOException;

    @GetRequest("messages/answerCallbackQuery")
    ApiResponse answerCallbackQuery(
        @RequestParam("queryId") final String queryId,
        @RequestParam("text") final String text,
        @RequestParam("showAlert") final Boolean showAlert,
        @RequestParam("url") final String url
    ) throws IOException;

}
