package ru.mail.im.botapi.sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mail.im.botapi.response.ApiResponse;

public class ResponsePrinter implements OnRequestExecuteListener {

    private final Gson gson = new GsonBuilder().create();

    @Override
    public void onRequestExecute(final ApiResponse response) {
        System.out.println(gson.toJson(response));
    }
}
