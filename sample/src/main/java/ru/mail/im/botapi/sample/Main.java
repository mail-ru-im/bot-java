package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.sample.command.CommandProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(final String[] args) throws IOException {
        InputStream in = System.in;
        if (args.length == 1) {
            in = new FileInputStream(args[0]);
        }

        final PlaceholderValueProviderImpl placeholderValueProvider = new PlaceholderValueProviderImpl();

        final AppCommandHandler appCommandHandler = new AppCommandHandler();
        appCommandHandler.addOnRequestExecuteListener(placeholderValueProvider);
        appCommandHandler.addOnRequestExecuteListener(new ResponsePrinter());

        final CommandProcessor parser = new CommandProcessor(in, System.out, appCommandHandler);
        parser.setPlaceholderValueProvider(placeholderValueProvider);
        parser.start();
    }
}
