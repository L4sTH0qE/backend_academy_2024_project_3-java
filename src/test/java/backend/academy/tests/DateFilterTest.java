package backend.academy.tests;

import backend.academy.model.LogAnalyzer;
import backend.academy.model.LogRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/// Проверка фильтрации по временному диапазону на различных случаях.
public class DateFilterTest {

    static final List<LogRecord> RECORDS = new ArrayList<>();

    static LogAnalyzer logAnalyzer;

    static LogRecord logRecordFirst =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(1),
            "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordSecond =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(2),
            "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordThird =
        new LogRecord("80.91.33.133", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(3),
            "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)");
    static LogRecord logRecordFourth =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(4),
            "GET /downloads/product_1 HTTP/1.1", 200,
            490, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");
    static LogRecord logRecordFifth =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(5),
            "GET /downloads/product_2 HTTP/1.1", 200,
            490, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");
    static LogRecord logRecordSixth =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(6),
            "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordSeventh =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.of(LocalDateTime.MIN, ZoneOffset.UTC).plusDays(7),
            "GET /downloads/product_2 HTTP/1.1", 404,
            337, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");

    @BeforeAll
    static void initialiseStream() {
        RECORDS.add(logRecordFirst);
        RECORDS.add(logRecordSecond);
        RECORDS.add(logRecordThird);
        RECORDS.add(logRecordFourth);
        RECORDS.add(logRecordFifth);
        RECORDS.add(logRecordSixth);
        RECORDS.add(logRecordSeventh);
    }

    @Test
    void testNoDateFilters() {
        logAnalyzer = new LogAnalyzer();
        logAnalyzer.updateLogReport("", null, null, RECORDS.stream());

        assertThat(logAnalyzer.logReport().totalRequests()).isEqualTo(7);
    }

    @Test
    void testAllLogsDateFilters() {
        logAnalyzer = new LogAnalyzer();
        logAnalyzer.updateLogReport("", LocalDate.MIN, LocalDate.MAX, RECORDS.stream());

        assertThat(logAnalyzer.logReport().totalRequests()).isEqualTo(7);
    }

    @Test
    void testSomeDateFilters() {
        logAnalyzer = new LogAnalyzer();
        logAnalyzer.updateLogReport("", LocalDate.MIN.plusDays(2), LocalDate.MIN.plusDays(6), RECORDS.stream());

        assertThat(logAnalyzer.logReport().totalRequests()).isEqualTo(4);
    }

    @Test
    void testStrictDateFilters() {
        logAnalyzer = new LogAnalyzer();
        logAnalyzer.updateLogReport("", LocalDate.MIN.plusDays(4), LocalDate.MIN.plusDays(4), RECORDS.stream());

        assertThat(logAnalyzer.logReport().totalRequests()).isEqualTo(0);
    }

    @Test
    void testMovedDateFilters() {
        logAnalyzer = new LogAnalyzer();
        logAnalyzer.updateLogReport("", LocalDate.MIN.plusDays(10), LocalDate.MIN.plusDays(20), RECORDS.stream());

        assertThat(logAnalyzer.logReport().totalRequests()).isEqualTo(0);
    }
}
