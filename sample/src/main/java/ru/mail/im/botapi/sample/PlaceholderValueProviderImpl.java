package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.response.ApiResponse;
import ru.mail.im.botapi.response.MessageResponse;
import ru.mail.im.botapi.sample.command.PlaceholderValueProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PlaceholderValueProviderImpl implements OnRequestExecuteListener, PlaceholderValueProvider {

    private final Pattern msgIdPattern = Pattern.compile("msgId\\[(-?[0-9]+)\\]");

    private final List<Long> lastMessageIds = new ArrayList<>();

    @Override
    public void onRequestExecute(final ApiResponse response) {
        if (response instanceof MessageResponse) {
            lastMessageIds.add(((MessageResponse) response).getMsgId());
        }
    }

    @Override
    public String provide(final String name) {
        Matcher matcher = msgIdPattern.matcher(name);
        if (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            if (index < 0) {
                index += lastMessageIds.size();
            }
            return lastMessageIds.get(index).toString();
        }
        return null;
    }
}
