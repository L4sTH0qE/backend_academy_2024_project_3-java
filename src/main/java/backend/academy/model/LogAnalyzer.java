package backend.academy.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;
import lombok.Getter;

/// Класс для формирования LogReport по потоку логов.
@Getter
public class LogAnalyzer {

    private final LogReport logReport = new LogReport();

    public void updateLogReport(String file, LocalDate fromDate, LocalDate toDate, Stream<LogRecord> fileLogStream) {

        logReport.updateFiles(file);

        OffsetDateTime offsetFromDate;
        OffsetDateTime offsetToDate;

        if (fromDate != null) {
            logReport.updateFromDate(fromDate);
            offsetFromDate = fromDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
        } else {
            offsetFromDate = OffsetDateTime.MIN;
        }
        if (toDate != null) {
            logReport.updateToDate(toDate);
            offsetToDate = toDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime();
        } else {
            offsetToDate = OffsetDateTime.MAX;
        }

        fileLogStream.forEach(log -> {
            if (log.timeLocal().isAfter(offsetFromDate) &&
                log.timeLocal().isBefore(offsetToDate)) {

                logReport.updateTotalRequests();
                logReport.updateBytesSent(log.bodyBytesSent());
                logReport.updateRequestInfo(log.status(), log.getRequestResource());
            }
        });
    }
}
