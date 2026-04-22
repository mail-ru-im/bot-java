package ru.mail.im.botapi.api;

import ru.mail.im.botapi.response.FileResponse;
import java.io.IOException;

public interface Files {

    @GetRequest("files/getInfo")
    FileResponse getInfo(@RequestParam("fileId") final String fileId) throws IOException;
}
