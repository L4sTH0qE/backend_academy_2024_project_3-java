package backend.academy;


import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import java.time.LocalDate;

/// Основной класс для работы с меню приложения.
@Log4j2
@UtilityClass
public class AppController {

    // Объект для потокового чтения источника
    LogProcessor logProcessor;

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


            logProcessor.getLogStream(path, fromDate, toDate);


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
