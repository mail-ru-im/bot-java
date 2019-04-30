package ru.mail.im.botapi.sample.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    private final Pattern paramsPattern = Pattern.compile("(\\w+)\\s*=\\s*\"([^\"]*)\"");

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
        if (command.startsWith("#")) {
            skipInputToLineEnd();
            return;
        }
        final Map<String, String> params = readParams();
        switch (command) {
            case "exit": {
                listener.onExit();
                running = false;
                break;
            }
            case "start": {
                listener.onStart(params.get("token"));
                break;
            }
            case "self": {
                listener.onSelf();
                break;
            }
            case "send": {
                final String chatId = params.get("to");
                if (params.containsKey("text")) {
                    listener.onSendText(chatId, params.get("text"));
                } else if (params.containsKey("file")) {
                    if (params.containsKey("caption")) {
                        listener.onSendFile(chatId, new File(params.get("file")), params.get("caption"));
                    } else {
                        listener.onSendFile(chatId, new File(params.get("file")));
                    }
                } else if (params.containsKey("voice")) {
                    listener.onSendVoice(chatId, new File(params.get("voice")));
                }
                break;
            }
        }
        print("OK");
    }

    private Map<String, String> readParams() {
        final String text = input.nextLine();
        final Matcher matcher = paramsPattern.matcher(text);
        final Map<String, String> params = new HashMap<>();
        while (matcher.find()) {
            params.put(matcher.group(1), matcher.group(2));
        }
        return params;
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
