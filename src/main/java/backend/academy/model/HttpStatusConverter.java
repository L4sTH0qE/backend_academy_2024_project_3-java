package backend.academy.model;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

/// Класс для хранения мапы с текстовым представлением числовых статусов ответа http-запроса.
@SuppressWarnings("MagicNumber") // Понимаю, что плохо, но это костыль во благо человечества.
@UtilityClass
public class HttpStatusConverter {

    private final Map<Integer, String> httpStatusMap = new HashMap<>();

    public void initialiseHttpStatusConverter() {
        httpStatusMap.put(100, "Continue");
        httpStatusMap.put(101, "Switching Protocols");
        httpStatusMap.put(200, "OK");
        httpStatusMap.put(201, "Created");
        httpStatusMap.put(202, "Accepted");
        httpStatusMap.put(203, "Non-Authoritative Information");
        httpStatusMap.put(204, "No Content");
        httpStatusMap.put(205, "Reset Content");
        httpStatusMap.put(206, "Partial Content");
        httpStatusMap.put(300, "Multiple Choices");
        httpStatusMap.put(301, "Moved Permanently");
        httpStatusMap.put(302, "Found");
        httpStatusMap.put(303, "See Other");
        httpStatusMap.put(304, "Not Modified");
        httpStatusMap.put(305, "Use Proxy");
        httpStatusMap.put(307, "Temporary Redirect");
        httpStatusMap.put(400, "Bad Request");
        httpStatusMap.put(401, "Unauthorized");
        httpStatusMap.put(402, "Payment Required");
        httpStatusMap.put(403, "Forbidden");
        httpStatusMap.put(404, "Not Found");
        httpStatusMap.put(405, "Method Not Allowed");
        httpStatusMap.put(406, "Not Acceptable");
        httpStatusMap.put(407, "Proxy Authentication Required");
        httpStatusMap.put(408, "Request Timeout");
        httpStatusMap.put(409, "Conflict");
        httpStatusMap.put(410, "Gone");
        httpStatusMap.put(411, "Length Required");
        httpStatusMap.put(412, "Precondition Failed");
        httpStatusMap.put(413, "Request Entity Too Large");
        httpStatusMap.put(414, "Request-URI Too Long");
        httpStatusMap.put(415, "Unsupported Media Type");
        httpStatusMap.put(416, "Requested Range Not Satisfiable");
        httpStatusMap.put(417, "Expectation Failed");
        httpStatusMap.put(500, "Internal Server Error");
        httpStatusMap.put(501, "Not Implemented");
        httpStatusMap.put(502, "Bad Gateway");
        httpStatusMap.put(503, "Service Unavailable");
        httpStatusMap.put(504, "Gateway Timeout");
        httpStatusMap.put(505, "HTTP Version Not Supported");
    }

    public String convertHttpStatus(int statusCode) {
        return httpStatusMap.getOrDefault(statusCode, "Unknown HTTP status code");
    }
}
