package backend.academy.view;

import backend.academy.model.FileFormat;
import java.util.Objects;
import lombok.experimental.UtilityClass;

/// Вспомогательный класс для выделения методов вывода в консоль, необходимых для использования в нескольких классах.
@UtilityClass
public class AppView {

    /// Метод для очистки окна консоли.
    @SuppressWarnings("RegexpSinglelineJava")
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /// Метод для вывода сообщения о завершении работы программы.
    @SuppressWarnings("RegexpSinglelineJava")
    public static void exit() {
        System.out.println("Exiting...");
    }

    /// Метод для вывода сообщения о записи отчета по обработке логов в файл соответственно указанному формату.
    @SuppressWarnings("RegexpSinglelineJava")
    public static void printEndMessage(FileFormat format) {
        System.out.println(
            "Log processing report is in: result."
                + (Objects.equals(format.formatName(), FileFormat.MARKDOWN.formatName()) ? "md" : "adoc")
                + "\n");
    }
}
