package backend.academy.controller;

import backend.academy.model.LogReport;
import backend.academy.processor.HttpLogProcessor;
import backend.academy.processor.LocalLogProcessor;
import backend.academy.processor.LogProcessor;
import backend.academy.view.AppView;
import backend.academy.writer.AdocLogWriter;
import backend.academy.writer.LogWriter;
import backend.academy.writer.MarkdownLogWriter;
import java.time.LocalDate;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/// Основной класс для работы с меню приложения.
@Log4j2
@UtilityClass
public class AppController {

    // Объект для потокового чтения источника
    LogProcessor logProcessor;

    // Объект для формирования файла-отчета по логам.
    LogWriter logWriter;

    /// Метод для отображения окна входа в программу.
    @SuppressWarnings("RegexpSinglelineJava")
    public static void start(String path, LocalDate fromDate, LocalDate toDate, String format) {
        try {
            AppView.clear();
            String lowerCasePath = path.trim().toLowerCase();
            boolean isWeb = lowerCasePath.startsWith("http://") || lowerCasePath.startsWith("https://");

            // Создаем объект анализатора в зависимости от того, берем ли мы логи локально или из сети.
            if (isWeb) {
                logProcessor = new HttpLogProcessor();
            } else {
                logProcessor = new LocalLogProcessor();
            }

            // Собираем статистику по логам.
            LogReport logReport = logProcessor.processLogStream(path, fromDate, toDate);

            // Если не получилось прочитать файл с логами - выходим из программы.
            if (logReport == null) {
                exit();
            }

            // Создаем объект писателя в зависимости от формата выходного файла.
            if (Objects.equals(format, "adoc")) {
                logWriter = new AdocLogWriter();
            } else {
                logWriter = new MarkdownLogWriter();
            }

            // Записываем статистику в файл.
            logWriter.generateFile(logReport);

            // Выводим сообщение о результате работы.
            AppView.printEndMessage(format);

            // Если возникла ошибка во время работы программы.
        } catch (Exception ex) {
            log.error("An unexpected error occurred while the program was running!");
            log.error("Details: {}", ex.getMessage());
            exit();
        }
    }

    /// Метод для завершения работы программы.
    @SuppressWarnings("RegexpSinglelineJava")
    public static void exit() {
        AppView.exit();
        System.exit(0);
    }
}
