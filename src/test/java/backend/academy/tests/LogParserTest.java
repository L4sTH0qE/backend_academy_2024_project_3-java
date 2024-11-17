package backend.academy.tests;

import backend.academy.model.LogParser;
import backend.academy.model.LogRecord;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/// Проверка парсера логов на корректность разбора формата логов.
public class LogParserTest {

    static final String correctLogLine =
        "80.91.33.133 - - [17/May/2015:08:05:04 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"";

    static final String incorrectLogLine = "";

    @Test
    void testParseCorrectLogLine() {
        LogRecord logRecord = LogParser.parseLogLine(correctLogLine);
        assertThat(logRecord).isNotNull();
        assertThat(logRecord.remoteAddr()).isEqualTo("80.91.33.133");
        assertThat(logRecord.remoteUser()).isEqualTo("-");
        assertThat(logRecord.timeLocal().toString()).isEqualTo("2015-05-17T08:05:04Z");
        assertThat(logRecord.request()).isEqualTo("GET /downloads/product_1 HTTP/1.1");
        assertThat(logRecord.status()).isEqualTo(304);
        assertThat(logRecord.bodyBytesSent()).isEqualTo(0);
        assertThat(logRecord.httpReferer()).isEqualTo("-");
        assertThat(logRecord.httpUserAgent()).isEqualTo("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)");
    }

    @Test
    void testParseIncorrectLogLine() {
        LogRecord logRecord = LogParser.parseLogLine(incorrectLogLine);
        assertThat(logRecord).isNull();
    }
}
