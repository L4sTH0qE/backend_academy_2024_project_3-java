package backend.academy;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Main {
    public static void main(String[] args) {
        String path = null;
        OffsetDateTime fromDateStr = null;
        OffsetDateTime toDateStr = null;
        String format = "markdown";

        try {
            for (int i = 0; i < args.length; ++i) {
                if (Objects.equals(args[i], "--path")) {
                    path = args[++i];
                } else if (Objects.equals(args[i], "--from")) {
                    fromDateStr = parseToOffsetDateTime(args[++i]);
                } else if (Objects.equals(args[i], "--to")) {
                    toDateStr = parseToOffsetDateTime(args[++i]);
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
            AppController.start(path, fromDateStr, toDateStr, format);
        } catch (Exception ex) { // Если возникла ошибка во время работы программы.
            log.error("An unexpected error occurred while the program was running!");
            log.error("Details: {}", ex.getMessage());
            AppController.exit();
        }
    }

    /// Метод для сообщения о неверно переданных аргументах.
    private static void invalidArgsExit() {
        log.error("Analyzer requires these arguments: --path <url/glob pattern> [--from <yyyy-MM-dd'T'HH:mm:ssXXX>] " +
            "[--to <yyyy-MM-dd'T'HH:mm:ssXXX>] [--format <markdown/adoc>]");
        AppController.exit();
    }

    /// Метод для приведения введенных from и to параметров к общему виду (yyyy-MM-dd'T'HH:mm:ssXXX).
    private static OffsetDateTime parseToOffsetDateTime(String dateStr) {
        // Регулярное выражение для определения формата даты и времени.
        String regex = "(\\d{4})(?:-(\\d{2})(?:-(\\d{2}))?)?" +
            "(?:[ T](\\d{2}):?(\\d{2})?(?::?(\\d{2}))?([+-]\\d{2}:?\\d{2})?)?";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateStr);

        if (matcher.matches()) {
            String yearPart = matcher.group(1);
            String monthPart = matcher.group(2);
            String dayPart = matcher.group(3);
            String hourPart = matcher.group(4);
            String minutePart = matcher.group(5);
            String secondPart = matcher.group(6);
            String offsetPart = matcher.group(7);

            // Установка дефолтных значений для неполных дат.
            if (monthPart == null) {
                monthPart = "01";
            }
            if (dayPart == null) {
                dayPart = "01";
            }
            if (hourPart == null) {
                hourPart = "00";
            }
            if (minutePart == null) {
                minutePart = "00";
            }
            if (secondPart == null) {
                secondPart = "00";
            }
            if (offsetPart == null) {
                offsetPart = "+00:00";
            }

            String formattedDateTime = yearPart + "-" + monthPart + "-" + dayPart +
                "T" + hourPart + ":" + minutePart + ":" + secondPart + offsetPart;

            return OffsetDateTime.parse(formattedDateTime);
        } else {
            throw new DateTimeParseException("Invalid date format", dateStr, 0);
        }
    }
}
