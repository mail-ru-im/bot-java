package ru.mail.im.botapi.sample.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.NoSuchElementException;
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

    private void processCommand(final String command) {
        switch (command) {
            case "exit": {
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
                final String contact = input.next();
                final String text = getTextOrSingleWord();
                listener.onSendText(contact, text);
                break;
            }
            case "sendFile": {
                final String contact = input.next();
                final String fileName = getTextOrSingleWord();
                listener.onSendFile(contact, new File(fileName));
                break;
            }
            case "sendVoice": {
                final String contact = input.next();
                final String fileName = getTextOrSingleWord();
                listener.onSendVoice(contact, new File(fileName));
                break;
            }
        }
        skipInputToLineEnd();
    }

    private String getTextOrSingleWord() {
        if (input.hasNext("\".*\"")) {
            final String text = input.findInLine("\".*\"");
            return text.substring(1, text.length() - 1);
        }
        if (input.hasNext()) {
            return input.next();
        }
        throw new NoSuchElementException();
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
