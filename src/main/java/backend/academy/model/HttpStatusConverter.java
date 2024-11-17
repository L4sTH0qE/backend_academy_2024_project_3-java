package backend.academy.model;

import lombok.experimental.UtilityClass;
import java.util.HashMap;
import java.util.Map;

/// Класс для хранения мапы с текстовым представлением числовых статусов ответа http-запроса.
@SuppressWarnings("MagicNumber") // Понимаю, что плохо, но это костыль во благо человечества.
@UtilityClass
public class HttpStatusConverter {

    private final Map<Integer, String> HTTP_STATUS_MAP = new HashMap<>();

    public void initialiseHttpStatusConverter() {
        HTTP_STATUS_MAP.put(100, "Continue");
        HTTP_STATUS_MAP.put(101, "Switching Protocols");
        HTTP_STATUS_MAP.put(200, "OK");
        HTTP_STATUS_MAP.put(201, "Created");
        HTTP_STATUS_MAP.put(202, "Accepted");
        HTTP_STATUS_MAP.put(203, "Non-Authoritative Information");
        HTTP_STATUS_MAP.put(204, "No Content");
        HTTP_STATUS_MAP.put(205, "Reset Content");
        HTTP_STATUS_MAP.put(206, "Partial Content");
        HTTP_STATUS_MAP.put(300, "Multiple Choices");
        HTTP_STATUS_MAP.put(301, "Moved Permanently");
        HTTP_STATUS_MAP.put(302, "Found");
        HTTP_STATUS_MAP.put(303, "See Other");
        HTTP_STATUS_MAP.put(304, "Not Modified");
        HTTP_STATUS_MAP.put(305, "Use Proxy");
        HTTP_STATUS_MAP.put(307, "Temporary Redirect");
        HTTP_STATUS_MAP.put(400, "Bad Request");
        HTTP_STATUS_MAP.put(401, "Unauthorized");
        HTTP_STATUS_MAP.put(402, "Payment Required");
        HTTP_STATUS_MAP.put(403, "Forbidden");
        HTTP_STATUS_MAP.put(404, "Not Found");
        HTTP_STATUS_MAP.put(405, "Method Not Allowed");
        HTTP_STATUS_MAP.put(406, "Not Acceptable");
        HTTP_STATUS_MAP.put(407, "Proxy Authentication Required");
        HTTP_STATUS_MAP.put(408, "Request Timeout");
        HTTP_STATUS_MAP.put(409, "Conflict");
        HTTP_STATUS_MAP.put(410, "Gone");
        HTTP_STATUS_MAP.put(411, "Length Required");
        HTTP_STATUS_MAP.put(412, "Precondition Failed");
        HTTP_STATUS_MAP.put(413, "Request Entity Too Large");
        HTTP_STATUS_MAP.put(414, "Request-URI Too Long");
        HTTP_STATUS_MAP.put(415, "Unsupported Media Type");
        HTTP_STATUS_MAP.put(416, "Requested Range Not Satisfiable");
        HTTP_STATUS_MAP.put(417, "Expectation Failed");
        HTTP_STATUS_MAP.put(500, "Internal Server Error");
        HTTP_STATUS_MAP.put(501, "Not Implemented");
        HTTP_STATUS_MAP.put(502, "Bad Gateway");
        HTTP_STATUS_MAP.put(503, "Service Unavailable");
        HTTP_STATUS_MAP.put(504, "Gateway Timeout");
        HTTP_STATUS_MAP.put(505, "HTTP Version Not Supported");
    }

    public String convertHttpStatus(int statusCode) {
        return HTTP_STATUS_MAP.getOrDefault(statusCode, "Unknown HTTP status code");
    }
}
