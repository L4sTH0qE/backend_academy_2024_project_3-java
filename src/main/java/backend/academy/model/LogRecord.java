package backend.academy.model;

import java.time.OffsetDateTime;

/// Класс-запись для хранения информации лога.
public record LogRecord(String remoteAddr, String remoteUser, OffsetDateTime timeLocal, String request, int status,
                        int bodyBytesSent, String httpReferer, String httpUserAgent) {
}
