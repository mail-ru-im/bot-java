package ru.mail.im.botapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotLogger {
    private static Logger logger = LoggerFactory.getLogger(BotLogger.class);
    private static String BOT_TAG = "IM_BOT";

    public static void i(String message) {
        logger.info(String.format("%s %s", BOT_TAG, message));
    }

    public static void e(Exception e) {
        logger.error(e.getMessage(), String.format("%s %s", BOT_TAG, e.getMessage()));
    }

    public static void e(Exception e, String message) {
        logger.error(String.format("%s %s", BOT_TAG, message), e);
    }
}
