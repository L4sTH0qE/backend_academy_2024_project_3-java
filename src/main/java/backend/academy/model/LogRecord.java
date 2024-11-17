package backend.academy.model;

import java.time.OffsetDateTime;

/// Класс-запись для хранения информации лога.
public record LogRecord(String remoteAddr, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bodyBytesSent, String httpReferer, String httpUserAgent) {

    /// Метод для получения запрашиваемого ресурса http-запроса из лога.
    public String getRequestResource() {
        return request.split(" ")[1];
    }

    /// Метод для получения используемого протокола http-запроса из лога.
    public String getRequestProtocol() {
        return request.split(" ")[2];
    }
}
