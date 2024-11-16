package backend.academy;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface LogProcessor {

    /// Метод для поточной обработки логов по указанному пути.
    void getLogStream(String path, LocalDate fromDateStr, LocalDate toDateStr);

    /// Метод для преобразования строки лога в объект LogRecord (для обработки данных в логе).
    static LogRecord parseLogLine(String line) {
        String[] logData = line.split(" ");
        final int remoteAddrId = 0;
        final int remoteUserId = 2;
        final int timeLocalId = 3;
        final int requestId = 4;
        final int statusId = 5;
        final int bodyBytesSentId = 6;
        final int httpRefererId = 7;
        final int httpUserAgentId = 8;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss xx", Locale.ENGLISH);

        String remoteAddr = logData[remoteAddrId];
        String remoteUser = logData[remoteUserId];
        OffsetDateTime
            timeLocal = OffsetDateTime.parse(logData[timeLocalId].substring(1, logData[timeLocalId].length() - 1), formatter);
        String request = logData[requestId].substring(1, logData[requestId].length() - 1);
        int status = Integer.parseInt(logData[statusId]);
        int bodyBytesSent = Integer.parseInt(logData[bodyBytesSentId]);
        String httpReferer = logData[httpRefererId].substring(1, logData[httpRefererId].length() - 1);;
        String httpUserAgent = logData[httpUserAgentId].substring(1, logData[httpUserAgentId].length() - 1);;

        return new LogRecord(remoteAddr, remoteUser, timeLocal, request, status, bodyBytesSent, httpReferer, httpUserAgent);
    }
}
