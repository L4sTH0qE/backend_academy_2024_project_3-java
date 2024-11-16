package backend.academy;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/// Интерфейс для алгоритма чтения файлов и получения из них потока с логами.
public interface LogProcessor {

    // Объект-анализатор для обработки потока логов.
    LogAnalyzer logAnalyzer = new LogAnalyzer();

    /// Метод для поточной обработки логов по указанному пути.
    void getLogStream(String path, LocalDate fromDateStr, LocalDate toDateStr);

    /// Метод для преобразования строки лога в объект LogRecord (для обработки данных в логе).
    static LogRecord parseLogLine(String line) {
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

        // Индексы для обращения к конкретным частям массива частей строк лога.
        final int remoteAddrId = 0;
        final int remoteUserId = 2;
        final int timeLocalId = 3;
        final int requestId = 4;
        final int statusId = 5;
        final int bodyBytesSentId = 6;
        final int httpRefererId = 7;
        final int httpUserAgentId = 8;

        // Паттерн для времени в строке лога.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss xx", Locale.ENGLISH);

        // Задаем переменные для LogRecord.
        String remoteAddr = logData[remoteAddrId];
        String remoteUser = logData[remoteUserId];
        OffsetDateTime timeLocal =
            OffsetDateTime.parse(logData[timeLocalId], formatter);
        String request = logData[requestId];
        int status = Integer.parseInt(logData[statusId]);
        int bodyBytesSent = Integer.parseInt(logData[bodyBytesSentId]);
        String httpReferer = logData[httpRefererId];
        String httpUserAgent = logData[httpUserAgentId];

        return new LogRecord(remoteAddr, remoteUser, timeLocal, request, status, bodyBytesSent, httpReferer,
            httpUserAgent);
    }
}
