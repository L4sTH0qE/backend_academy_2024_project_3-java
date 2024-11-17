package backend.academy.tests;

import backend.academy.processor.HttpLogProcessor;
import backend.academy.processor.LocalLogProcessor;
import backend.academy.processor.LogProcessor;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/// Проверка чтения файлов по локальному пути и через URL.
public class ReadLogFileTest {

    static final String URL_PATH =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    static final String INCORRECT_URL_PATH = "https://badaboy.com/bebra";
    static final String LOCAL_PATH = "src/test/java/backend/academy/data/**/*Log*";
    static final String INCORRECT_LOCAL_PATH = "bebra/*";

    static LogProcessor httpLogProcessor = new HttpLogProcessor();
    static LogProcessor localLogProcessor = new LocalLogProcessor();

    @Test
    void testCorrectPathURL() {
        assertThat(httpLogProcessor.processLogStream(URL_PATH, null, null)).isNotNull();
    }

    @Test
    void testCorrectPathLocal() {
        assertThat(localLogProcessor.processLogStream(LOCAL_PATH, null, null)).isNotNull();
    }

    @Test
    void testIncorrectPathURL() {
        assertThat(httpLogProcessor.processLogStream(INCORRECT_URL_PATH, null, null)).isNull();
    }

    @Test
    void testIncorrectPathLocal() {
        assertThat(localLogProcessor.processLogStream(INCORRECT_LOCAL_PATH, null, null)).isNull();
    }
}
