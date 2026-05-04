package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

public class FileResponse extends ApiResponse {

    @SerializedName("type")
    private String type;

    @SerializedName("size")
    private long size;

    @SerializedName("filename")
    private String filename;

    @SerializedName("url")
    private String url;

    public String getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    public String getFilename() {
        return filename;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "type=" + type + '\'' +
                ", size=" + size +
                ", filename='" + filename + '\'' +
                ", url='" + url +
                '}';
    }
}
