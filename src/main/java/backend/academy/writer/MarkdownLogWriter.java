package backend.academy.writer;

import backend.academy.model.HttpStatusConverter;
import backend.academy.model.LogReport;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

/// Класс, реализующий способ генерации файла формата .md с собранной по логам статистикой.
@Log4j2
public class MarkdownLogWriter implements LogWriter {

    @Override
    public void generateFile(LogReport logReport) {

        // Инициализируем мапу кодов ответа http-запроса.
        HttpStatusConverter.initialiseHttpStatusConverter();

        // Путь до файла, в который будем записывать отчет.
        Path fileName = Paths.get("result.md");

        String tableEndLine = " |\n";
        String tableColumn = " | ";

        try {
            Files.writeString(fileName, "#### Общая информация\n\n");
            Files.writeString(fileName, "| Метрика | Значение |\n", StandardOpenOption.APPEND);

            int fileNameLength = 0;
            for (String logFile : logReport.files()) {
                fileNameLength += logFile.length() + 2;
            }
            Files.writeString(fileName,
                "|:-------------------------:|" + StringUtils.repeat("-", fileNameLength) + ":|\n",
                StandardOpenOption.APPEND);

            StringBuilder files = new StringBuilder();
            for (String logFile : logReport.files()) {
                files.append('`').append(logFile).append('`').append(", ");
            }
            files.deleteCharAt(files.length() - 1);
            files.deleteCharAt(files.length() - 1);

            Files.writeString(fileName, "| Файл(-ы) | " + files + tableEndLine, StandardOpenOption.APPEND);
            Files.writeString(fileName,
                "| Начальная дата | " + (logReport.fromDate() == null ? "-" : logReport.fromDate()) + tableEndLine,
                StandardOpenOption.APPEND);
            Files.writeString(fileName,
                "| Конечная дата | " + (logReport.toDate() == null ? "-" : logReport.toDate()) + tableEndLine,
                StandardOpenOption.APPEND);

            Files.writeString(fileName, "| Количество запросов | " + logReport.totalRequests() + tableEndLine,
                StandardOpenOption.APPEND);
            Files.writeString(fileName,
                "| Средний размер ответа (в байтах) | " + logReport.getBytesMean() + tableEndLine,
                StandardOpenOption.APPEND);
            Files.writeString(fileName,
                "| 95p размера ответа (в байтах) | " + logReport.getBytesPercentile(PERCENTILE) + tableEndLine,
                StandardOpenOption.APPEND);
            Files.writeString(fileName,
                "| Количество разных клиентов (ip-адресов) | " + logReport.remoteAddresses().size() + tableEndLine,
                StandardOpenOption.APPEND);

            Files.writeString(fileName, "\n#### Запрашиваемые ресурсы\n\n", StandardOpenOption.APPEND);
            Files.writeString(fileName, "| Ресурс | Количество |\n", StandardOpenOption.APPEND);
            Files.writeString(fileName, "|:---------------:|-----------:|\n", StandardOpenOption.APPEND);
            for (Map.Entry<String, Integer> resource : logReport.getMostFrequentResources()) {
                Files.writeString(fileName, "| `" + resource.getKey() + "` | " + resource.getValue() + tableEndLine,
                    StandardOpenOption.APPEND);
            }

            Files.writeString(fileName, "\n#### Коды ответа\n\n", StandardOpenOption.APPEND);
            Files.writeString(fileName, "| Код | Имя | Количество |\n", StandardOpenOption.APPEND);
            Files.writeString(fileName, "|:---:|:---------------------:|-----------:|\n", StandardOpenOption.APPEND);
            for (Map.Entry<Integer, Integer> status : logReport.getMostFrequentStatuses()) {
                Files.writeString(fileName,
                    "| " + status.getKey() + tableColumn + HttpStatusConverter.convertHttpStatus(status.getKey())
                        + tableColumn + status.getValue() + tableEndLine, StandardOpenOption.APPEND);
            }

        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
