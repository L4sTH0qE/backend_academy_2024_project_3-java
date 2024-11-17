package backend.academy.tests;

import backend.academy.model.LogAnalyzer;
import backend.academy.model.LogRecord;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/// Проверка правильности подсчета статистики.
public class LogReportTest {

    static final LogAnalyzer LOG_ANALYZER = new LogAnalyzer();

    static final int PERCENTILE = 95;

    static LogRecord logRecordFirst =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordSecond =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordThird =
        new LogRecord("80.91.33.133", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)");
    static LogRecord logRecordFourth =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_1 HTTP/1.1", 200,
            490, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");
    static LogRecord logRecordFifth =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_2 HTTP/1.1", 200,
            490, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");
    static LogRecord logRecordSixth =
        new LogRecord("93.180.71.3", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_1 HTTP/1.1", 304, 0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)");
    static LogRecord logRecordSeventh =
        new LogRecord("217.168.17.5", "-", OffsetDateTime.MIN.plusDays(1), "GET /downloads/product_2 HTTP/1.1", 404,
            337, "-",
            "Debian APT-HTTP/1.3 (0.8.10.3)");

    @BeforeAll
    static void initialiseStream() {
        List<LogRecord> recordsList = new ArrayList<>();
        recordsList.add(logRecordFirst);
        recordsList.add(logRecordSecond);
        recordsList.add(logRecordThird);
        recordsList.add(logRecordFourth);
        recordsList.add(logRecordFifth);
        recordsList.add(logRecordSixth);
        recordsList.add(logRecordSeventh);

        LOG_ANALYZER.updateLogReport("", null, null, recordsList.stream());
    }

    @Test
    void testLogReportNumericValues() {

        assertThat(LOG_ANALYZER.logReport().totalRequests()).isEqualTo(7);
        assertThat(LOG_ANALYZER.logReport().getBytesMean()).isEqualTo(188);
        assertThat(LOG_ANALYZER.logReport().getBytesPercentile(PERCENTILE)).isEqualTo(490);
    }

    @Test
    void testLogReportResources() {
        List<Map.Entry<String, Integer>> resources = LOG_ANALYZER.logReport().getMostFrequentResources();

        assertThat(resources.get(0).getKey()).isEqualTo("/downloads/product_1");
        assertThat(resources.get(0).getValue()).isEqualTo(5);
        assertThat(resources.get(1).getKey()).isEqualTo("/downloads/product_2");
        assertThat(resources.get(1).getValue()).isEqualTo(2);
    }

    @Test
    void testLogReportStatuses() {
        List<Map.Entry<Integer, Integer>> statuses = LOG_ANALYZER.logReport().getMostFrequentStatuses();

        assertThat(statuses.get(0).getKey()).isEqualTo(304);
        assertThat(statuses.get(0).getValue()).isEqualTo(4);
        assertThat(statuses.get(1).getKey()).isEqualTo(200);
        assertThat(statuses.get(1).getValue()).isEqualTo(2);
        assertThat(statuses.get(2).getKey()).isEqualTo(404);
        assertThat(statuses.get(2).getValue()).isEqualTo(1);
    }
}
