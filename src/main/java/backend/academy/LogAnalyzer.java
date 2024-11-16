package backend.academy;

import lombok.Getter;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

/// Класс для формирования LogReport по потоку логов.
@Getter
public class LogAnalyzer {

    private final LogReport logReport = new LogReport();

    public void updateLogReport(String file, LocalDate fromDate, LocalDate toDate, Stream<LogRecord> fileLogStream) {

        logReport.updateFiles(file);

        if (fromDate != null) {
            logReport.updateFromDate(fromDate);
        }
        if (toDate != null) {
            logReport.updateToDate(toDate);
        }

        fileLogStream.forEach(log -> {
            if ((fromDate == null || log.timeLocal().isAfter(OffsetDateTime.from(fromDate))) &&
                (toDate == null || log.timeLocal().isBefore(OffsetDateTime.from(toDate)))) {

                logReport.updateTotalRequests();
                logReport.updateBytesSent(log.bodyBytesSent());
                logReport.updateRequestInfo(log.status(), log.getRequestResource());
            }
        });
    }
}
