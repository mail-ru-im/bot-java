package ru.mail.im.botapi.sample.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class CommandProcessor {
    private final Scanner input;
    private final Writer output;
    private final CommandListener listener;

    private boolean running;

    public CommandProcessor(final InputStream input, final OutputStream output, final CommandListener listener) {
        this.input = new Scanner(input);
        this.output = new OutputStreamWriter(output);
        this.listener = listener;
    }

    public void start() throws IOException {
        running = true;
        while (running) {
            printPrompt();
            processCommand(input.next());
        }
    }

    private void processCommand(final String command) throws IOException {
        switch (command) {
            case "exit": {
                listener.onExit();
                running = false;
                break;
            }
            case "start": {
                listener.onStart(input.next());
                break;
            }
            case "self": {
                listener.onSelf();
                break;
            }
            case "sendText": {
                final String chatId = input.next();
                final String text = getTextOrSingleWord();
                listener.onSendText(chatId, text);
                break;
            }
            case "sendFile": {
                final String chatId = input.next();
                final String fileName = getTextOrSingleWord();
                listener.onSendFile(chatId, new File(fileName));
                break;
            }
            case "sendVoice": {
                final String chatId = input.next();
                final String fileName = getTextOrSingleWord();
                listener.onSendVoice(chatId, new File(fileName));
                break;
            }
        }
        skipInputToLineEnd();
        print("OK");
    }

    private String getTextOrSingleWord() {
        final String text = input.findInLine("\".*\"");
        if (text != null) {
            return text.substring(1, text.length() - 1);
        }
        return input.next();
    }

    private void skipInputToLineEnd() {
        input.nextLine();
    }

    private void printPrompt() throws IOException {
        output.write("$bot>");
        output.flush();
    }

    private void print(final String message) throws IOException {
        output.write(message);
        output.write(System.lineSeparator());
        output.flush();
    }
}
