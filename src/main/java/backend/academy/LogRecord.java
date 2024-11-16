package backend.academy;

import java.time.OffsetDateTime;

public record LogRecord(String remoteAddr, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bodyBytesSent, String httpReferer, String httpUserAgent) {

    public String getRequestMethod() {
        return request.split(" ")[0];
    }

    public String getRequestResource() {
        return request.split(" ")[1];
    }

    public String getRequestProtocol() {
        return request.split(" ")[2];
    }
}
