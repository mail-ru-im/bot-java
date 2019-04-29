package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.sample.command.CommandProcessor;

import java.io.IOException;

public class Main {
    public static void main(final String[] args) throws IOException {
        final CommandProcessor parser = new CommandProcessor(System.in, System.out, new AppCommandListener());
        parser.start();
    }
}
