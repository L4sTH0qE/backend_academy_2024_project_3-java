package backend.academy.processor;

import backend.academy.model.LogReport;
import java.time.LocalDate;

/// Интерфейс для алгоритма чтения файлов и получения из них потока с логами.
public interface LogProcessor {

    /// Метод для поточной обработки логов по указанному пути.
    LogReport processLogStream(String path, LocalDate fromDateStr, LocalDate toDateStr);
}
