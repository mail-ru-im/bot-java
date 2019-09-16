package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.BotApiClient;
import ru.mail.im.botapi.BotApiClientController;
import ru.mail.im.botapi.entity.ChatAction;

import java.io.IOException;
import java.util.Scanner;

public class SimpleStart {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter bot token:");
        String token = scanner.nextLine();
        System.out.println("Enter test chatId:");
        String chatId = scanner.nextLine();

        BotApiClient client = new BotApiClient(token, 0, 100);

        BotApiClientController controller = BotApiClientController.startBot(client);
        System.out.println("STARTED");
        client.addOnEventFetchListener(System.out::println);
        try {
            controller.sendActions(chatId, ChatAction.TYPING);
            long messageId = controller.sendTextMessage(chatId, "test").getMsgId();
            controller.sendActions(chatId, ChatAction.TYPING);
            controller.editText(chatId, messageId, "EDITED TEST");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!scanner.nextLine().equals("end")){
            //do nothing here, just waiting for fetch
        }
    }
}
