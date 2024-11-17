package backend.academy.model;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.Getter;
import lombok.experimental.UtilityClass;

/// Класс для парсинга строки лог-файла.
@UtilityClass
public class LogParser {

    /// Перечисление для элементов лога.
    @Getter public enum LogIndex {
        REMOTE_ADDR(0),
        REMOTE_USER(2),
        TIME_LOCAL(3),
        REQUEST(4),
        STATUS(5),
        BODY_BYTES_SENT(6),
        HTTP_REFERER(7),
        HTTP_USER_AGENT(8);

        private final int index;

        LogIndex(int index) {
            this.index = index;
        }
    }

    /// Метод для преобразования строки лога в объект LogRecord (для обработки данных в логе).
    public LogRecord parseLogLine(String line) {
        // Кастомное регулярное выражение для сплита строки лога (а почему бы и нет).
        String regex =
            "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+\\[(.*?)\\]\\s+\"(.*?)\"\\s+(\\d+)\\s+(\\d+)\\s+\"(.*?)\"\\s+\"(.*?)\"";

        // Используем регулярное выражение для сплита строки лога.
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(line);

        // Массив для хранения отдельных частей строки лога.
        final int logDataSize = 9;
        String[] logData = new String[logDataSize];

        if (matcher.matches()) {
            for (int i = 0; i < matcher.groupCount(); i++) {
                logData[i] = matcher.group(i + 1);
            }
        }

        // Паттерн для времени в строке лога.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss xx", Locale.ENGLISH);

        // Задаем переменные для LogRecord.
        String remoteAddr = logData[LogIndex.REMOTE_ADDR.index()];
        String remoteUser = logData[LogIndex.REMOTE_USER.index()];
        OffsetDateTime timeLocal =
            OffsetDateTime.parse(logData[LogIndex.TIME_LOCAL.index()], formatter);
        String request = logData[LogIndex.REQUEST.index()];
        int status = Integer.parseInt(logData[LogIndex.STATUS.index()]);
        int bodyBytesSent = Integer.parseInt(logData[LogIndex.BODY_BYTES_SENT.index()]);
        String httpReferer = logData[LogIndex.HTTP_REFERER.index()];
        String httpUserAgent = logData[LogIndex.HTTP_USER_AGENT.index()];

        return new LogRecord(remoteAddr, remoteUser, timeLocal, request, status, bodyBytesSent, httpReferer,
            httpUserAgent);
    }
}
