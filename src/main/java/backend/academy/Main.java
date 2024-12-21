package backend.academy;

import backend.academy.controller.AppController;
import backend.academy.model.FileFormat;
import java.time.LocalDate;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@Log4j2
public class Main implements Runnable {

    @Option(names = {"--path"},
        description = "Path to log file. Can be local file (with glob patterns) or URL.",
        required = true)
    private String path;

    @Option(names = {"--from"},
        description = "Start date in format yyyy-MM-dd.")
    private String fromDateStr;

    @Option(names = {"--to"},
        description = "End date in format yyyy-MM-dd.")
    private String toDateStr;

    @Option(names = {"--format"},
        description = "Output file format.")
    private String format;

    @Override
    public void run() {
        LocalDate fromDate = null;
        LocalDate toDate = null;
        FileFormat fileFormat = FileFormat.MARKDOWN;
        try {
            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                fromDate = LocalDate.parse(fromDateStr);
            }
            if (toDateStr != null && !toDateStr.isEmpty()) {
                toDate = LocalDate.parse(toDateStr);
            }
            if (Objects.equals(format, FileFormat.ADOC.formatName())) {
                fileFormat = FileFormat.ADOC;
            }
        } catch (Exception ex) {
            invalidArgsExit();
        }
        if (path == null || path.isEmpty()) {
            invalidArgsExit();
        }

        try {
            AppController.start(path, fromDate, toDate, fileFormat);
        } catch (Exception ex) { // Если возникла ошибка во время работы программы.
            log.error("An unexpected error occurred while the program was running!");
            log.error("Details: {}", ex.getMessage());
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    /// Метод для сообщения о неверно переданных аргументах.
    private static void invalidArgsExit() {
        log.error("Analyzer requires these arguments: --path <url/glob pattern> [--from <yyyy-MM-dd>] "
            + "[--to <yyyy-MM-dd>] [--format <markdown/adoc>]");
        AppController.exit();
    }
}
