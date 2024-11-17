package backend.academy.tests;

import backend.academy.model.LogReport;
import backend.academy.writer.AdocLogWriter;
import backend.academy.writer.LogWriter;
import backend.academy.writer.MarkdownLogWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/// Проверка формата и содержимого выходного отчета.
public class OutputFileFormatTest {

    static final Path MARKDOWN_PATH = Paths.get("results", "result.md");
    static final Path ADOC_PATH = Paths.get("results", "result.adoc");

    static LogWriter markdownLogWriter = new MarkdownLogWriter();
    static LogWriter adocLogWriter = new AdocLogWriter();

    static LogReport logReport = new LogReport();

    @BeforeAll
    static void initialiseLogReport() {
        logReport.updateFiles("src\\test\\java\\backend\\academy\\data\\logs\\firstLogFile");
        logReport.updateFiles("src\\test\\java\\backend\\academy\\data\\logs\\secondLogFile");

        for (int i = 0; i < 7; ++i) {
            logReport.updateTotalRequests();
        }

        logReport.updateFromDate(LocalDate.of(2015, 1, 1));
        logReport.updateToDate(null);

        for (int i = 0; i < 4; ++i) {
            logReport.updateBytesSent(0);
        }
        for (int i = 0; i < 2; ++i) {
            logReport.updateBytesSent(490);
        }
        logReport.updateBytesSent(337);

        logReport.updateRequestInfo(304, "/downloads/product_1");
        logReport.updateRequestInfo(304, "/downloads/product_1");
        logReport.updateRequestInfo(304, "/downloads/product_1");
        logReport.updateRequestInfo(200, "/downloads/product_1");
        logReport.updateRequestInfo(200, "/downloads/product_2");
        logReport.updateRequestInfo(304, "/downloads/product_1");
        logReport.updateRequestInfo(404, "/downloads/product_2");
    }

    @BeforeEach
    void deleteFilesBefore() {
        try {
            Files.deleteIfExists(MARKDOWN_PATH);
        } catch (Exception _) {
        }

        try {
            Files.deleteIfExists(ADOC_PATH);
        } catch (Exception _) {
        }
    }

    @AfterEach
    void deleteFilesAfter() {
        try {
            Files.deleteIfExists(MARKDOWN_PATH);
        } catch (Exception _) {
        }

        try {
            Files.deleteIfExists(ADOC_PATH);
        } catch (Exception _) {
        }
    }

    @Test
    void testFileMarkdown() {
        markdownLogWriter.generateFile(logReport);

        File output = new File(MARKDOWN_PATH.toString());
        assertThat(output.exists()).isTrue();

        String content = "";
        try {
            content = Files.readString(MARKDOWN_PATH, StandardCharsets.UTF_8);
        } catch (Exception _) {
            assertThat(false).isTrue();
        }

        assertThat(content).isEqualTo("""
            #### Общая информация

            | Метрика | Значение |
            |:-------------------------:|-------------------------------------------------------------------------------------------------------------:|
            | Файл(-ы) | `src\\test\\java\\backend\\academy\\data\\logs\\firstLogFile`, `src\\test\\java\\backend\\academy\\data\\logs\\secondLogFile` |
            | Начальная дата | 2015-01-01 |
            | Конечная дата | - |
            | Количество запросов | 7 |
            | Средний размер ответа (в байтах) | 188 |
            | 95p размера ответа (в байтах) | 490 |

            #### Запрашиваемые ресурсы

            | Ресурс | Количество |
            |:---------------:|-----------:|
            | `/downloads/product_1` | 5 |
            | `/downloads/product_2` | 2 |

            #### Коды ответа

            | Код | Имя | Количество |
            |:---:|:---------------------:|-----------:|
            | 304 | Not Modified | 4 |
            | 200 | OK | 2 |
            | 404 | Not Found | 1 |
            """);
    }

    @Test
    void testFileAdoc() {
        adocLogWriter.generateFile(logReport);

        File output = new File(ADOC_PATH.toString());
        assertThat(output.exists()).isTrue();

        String content = "";
        try {
            content = Files.readString(ADOC_PATH, StandardCharsets.UTF_8);
        } catch (Exception _) {
            assertThat(false).isTrue();
        }
        assertThat(content).isEqualTo("""
            ==== Общая информация

            [cols="1,1", options="header"]
            |===
            | Метрика | Значение
            | Файл(-ы) | `src\\test\\java\\backend\\academy\\data\\logs\\firstLogFile`, `src\\test\\java\\backend\\academy\\data\\logs\\secondLogFile`\s
            | Начальная дата | 2015-01-01
            | Конечная дата | -
            | Количество запросов | 7
            | Средний размер ответа (в байтах) | 188
            | 95p размера ответа (в байтах) | 490
            |===

            ==== Запрашиваемые ресурсы

            [cols="1,1", options="header"]
            |===
            | Ресурс | Количество
            | `/downloads/product_1` | 5
            | `/downloads/product_2` | 2
            |===

            ==== Коды ответа

            [cols="1,1,1", options="header"]
            |===
            | Код | Имя | Количество
            | 304 | Not Modified | 4
            | 200 | OK | 2
            | 404 | Not Found | 1
            |===
            """);
    }
}
