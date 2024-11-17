package backend.academy.processor;

import backend.academy.model.LogRecord;
import backend.academy.model.LogReport;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

/// Класс для чтения локальных файлов по glob-паттерну и получения из них потока с логами.
@Log4j2
public class LocalLogProcessor implements LogProcessor {

    @Override
    public LogReport processLogStream(String path, LocalDate fromDate, LocalDate toDate) {
        String baseDir = "";

        try {
            Path baseDirPath = Paths.get(baseDir).normalize();
            PathMatcher matcher = baseDirPath.getFileSystem().getPathMatcher("glob:" + path);

            try (Stream<Path> paths = Files.walk(baseDirPath)) {
                paths.filter(matcher::matches).forEach(p -> {
                    try (Stream<String> lines = Files.lines(p, StandardCharsets.UTF_8)) {
                        Stream<LogRecord> records = lines.map(LogProcessor::parseLogLine);
                        logAnalyzer.updateLogReport(p.toString(), fromDate, toDate, records);
                    } catch (IOException ex) {
                        log.error("0");
                    }
                });
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return logAnalyzer.logReport();
    }
}
