package ru.mail.im.botapi.sample.command;

import ru.mail.im.botapi.entity.ChatAction;

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
    private final Pattern placeholderPattern = Pattern.compile("\\$\\{([^}]*)}");

    private final Scanner input;
    private final Writer output;
    private final CommandHandler handler;

    private boolean running;
    private PlaceholderValueProvider placeholderValueProvider;

    public CommandProcessor(final InputStream input, final OutputStream output, final CommandHandler handler) {
        this.input = new Scanner(input);
        this.output = new OutputStreamWriter(output);
        this.handler = handler;
    }

    public void setPlaceholderValueProvider(final PlaceholderValueProvider placeholderValueProvider) {
        this.placeholderValueProvider = placeholderValueProvider;
    }

    public void start() throws IOException {
        running = true;
        while (running) {
            printPrompt();
            processInput(input.next());
        }
    }

    private void processInput(final String command) throws IOException {
        if (command.startsWith("#")) {
            skipInputToLineEnd();
        } else {
            processCommand(command);
        }
        print("OK");
    }

    private void processCommand(final String command) {
        final Map<String, String> params = readParams();
        switch (command) {
            case "exit": {
                handler.onExit();
                running = false;
                break;
            }
            case "sleep": {
                handler.onSleep(Long.parseLong(params.get("ms")));
                break;
            }
            case "start": {
                handler.onStart(params.get("token"));
                break;
            }
            case "self": {
                handler.onSelf();
                break;
            }
            case "send": {
                final String chatId = params.get("to");
                if (params.containsKey("text")) {
                    handler.onSendText(chatId, params.get("text"));
                } else if (params.containsKey("file")) {
                    if (params.containsKey("caption")) {
                        handler.onSendFile(chatId, new File(params.get("file")), params.get("caption"));
                    } else {
                        handler.onSendFile(chatId, new File(params.get("file")));
                    }
                } else if (params.containsKey("voice")) {
                    handler.onSendVoice(chatId, new File(params.get("voice")));
                }
                break;
            }
            case "edit": {
                handler.onEditText(params.get("chat"), Long.parseLong(params.get("msg")), params.get("text"));
                break;
            }
            case "delMsg": {
                final String chatId = params.get("chat");
                if (params.containsKey("msg")) {
                    handler.onDelete(chatId, Long.parseLong(params.get("msg")));
                } else if (params.containsKey("msgs")) {
                    final String[] list = params.get("msgs").split(",");
                    final long[] ids = new long[list.length];
                    for (int i = 0; i < list.length; i++) {
                        ids[i] = Long.parseLong(list[i]);
                    }
                    handler.onDelete(chatId, ids);
                }
                break;
            }
            case "notify": {
                final String chatId = params.get("chat");
                if (params.containsKey("action")) {
                    handler.onChatAction(chatId, ChatAction.valueOf(params.get("action")));
                } else if (params.containsKey("actions")) {
                    final String[] list = params.get("actions").split(",");
                    final ChatAction[] actions = new ChatAction[list.length];
                    for (int i = 0; i < list.length; i++) {
                        actions[i] = ChatAction.valueOf(list[i]);
                    }
                    handler.onChatAction(chatId, actions);
                } else {
                    handler.onChatAction(chatId);
                }
                break;
            }
            case "getInfo": {
                handler.onGetChatInfo(params.get("chat"));
                break;
            }
        }
    }

    private Map<String, String> readParams() {
        final String text = input.nextLine();
        final Matcher matcher = paramsPattern.matcher(text);
        final Map<String, String> params = new HashMap<>();
        while (matcher.find()) {
            params.put(matcher.group(1), replacePlaceholders(matcher.group(2)));
        }
        return params;
    }

    private String replacePlaceholders(String value) {
        final Matcher matcher = placeholderPattern.matcher(value);
        while (matcher.find()) {
            value = value.replace(matcher.group(0), placeholderValueProvider.provide(matcher.group(1)));
        }
        return value;
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
