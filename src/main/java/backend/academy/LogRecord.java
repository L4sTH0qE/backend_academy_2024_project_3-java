package backend.academy;

import java.time.OffsetDateTime;

/// Класс-запись для хранения информации лога.
public record LogRecord(String remoteAddr, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bodyBytesSent, String httpReferer, String httpUserAgent) {

    /// Метод для получения метода http-запроса из лога.
    public String getRequestMethod() {
        return request.split(" ")[0];
    }

    /// Метод для получения запрашиваемого ресурса http-запроса из лога.
    public String getRequestResource() {
        return request.split(" ")[1];
    }

    /// Метод для получения используемого протокола http-запроса из лога.
    public String getRequestProtocol() {
        return request.split(" ")[2];
    }

    /// Метод для получения текстового представления кода ответа http-запроса из лога.
    public String getStatusText() {
        return HttpStatusConverter.convertHttpStatus(status);
    }
}
