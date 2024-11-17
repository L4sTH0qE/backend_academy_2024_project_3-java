package backend.academy;

import java.time.LocalDate;
import java.util.Objects;
import backend.academy.controller.AppController;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        String path = null;
        LocalDate fromDate = null;
        LocalDate toDate = null;
        String format = "markdown";

        try {
            for (int i = 0; i < args.length; ++i) {
                if (Objects.equals(args[i], "--path")) {
                    path = args[++i];
                } else if (Objects.equals(args[i], "--from")) {
                    fromDate = LocalDate.parse(args[++i]);
                } else if (Objects.equals(args[i], "--to")) {
                    toDate = LocalDate.parse(args[++i]);
                } else if (Objects.equals(args[i], "--format")) {
                    String outputFormat = args[++i];
                    if (Objects.equals(outputFormat, "markdown") || Objects.equals(outputFormat, "adoc")) {
                        format = outputFormat;
                    } else {
                        System.out.println("Invalid format type. Set to default: markdown");
                    }
                }
            }
        } catch (Exception ex) {
            invalidArgsExit();
        }
        if (path == null) {
            invalidArgsExit();
        }

        try {
            AppController.start(path, fromDate, toDate, format);
        } catch (Exception ex) { // Если возникла ошибка во время работы программы.
            log.error("An unexpected error occurred while the program was running!");
            log.error("Details: {}", ex.getMessage());
            AppController.exit();
        }
    }

    /// Метод для сообщения о неверно переданных аргументах.
    private static void invalidArgsExit() {
        log.error("Analyzer requires these arguments: --path <url/glob pattern> [--from <yyyy-MM-dd>] " +
            "[--to <yyyy-MM-dd>] [--format <markdown/adoc>]");
        AppController.exit();
    }
}
