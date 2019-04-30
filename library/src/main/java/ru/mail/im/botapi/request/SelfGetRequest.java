package ru.mail.im.botapi.request;

import ru.mail.im.botapi.response.SelfGetResponse;

public class SelfGetRequest extends GetRequest<SelfGetResponse> {

    public SelfGetRequest() {
        super("self/get", SelfGetResponse.class);
    }
}
