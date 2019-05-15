package ru.mail.im.botapi.sample;

import ru.mail.im.botapi.response.ApiResponse;

interface OnRequestExecuteListener {
    void onRequestExecute(final ApiResponse response);
}
