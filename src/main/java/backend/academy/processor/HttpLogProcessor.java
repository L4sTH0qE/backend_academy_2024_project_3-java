package backend.academy.processor;

import backend.academy.model.LogAnalyzer;
import backend.academy.model.LogParser;
import backend.academy.model.LogRecord;
import backend.academy.model.LogReport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;

/// Класс для чтения веб-файла и получения из него потока с логами.
@Log4j2
public class HttpLogProcessor implements LogProcessor {

    @Override
    public LogReport processLogStream(String path, LocalDate fromDate, LocalDate toDate) {

        // Объект-анализатор для обработки потока логов.
        LogAnalyzer logAnalyzer = new LogAnalyzer();

        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(path))
                .GET()
                .build();

            try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(client.send(request, HttpResponse.BodyHandlers.ofInputStream()).body(),
                    StandardCharsets.UTF_8))) {
                try (Stream<String> lines = reader.lines()) {
                    Stream<LogRecord> records = lines.map(LogParser::parseLogLine);
                    logAnalyzer.updateLogReport(path, fromDate, toDate, records);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
        return logAnalyzer.logReport();
    }
}
