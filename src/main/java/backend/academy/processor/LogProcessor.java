package backend.academy.processor;

import backend.academy.model.LogAnalyzer;
import backend.academy.model.LogReport;

import java.time.LocalDate;

/// Интерфейс для алгоритма чтения файлов и получения из них потока с логами.
public interface LogProcessor {

    // Объект-анализатор для обработки потока логов.
    LogAnalyzer logAnalyzer = new LogAnalyzer();

    /// Метод для поточной обработки логов по указанному пути.
    LogReport processLogStream(String path, LocalDate fromDateStr, LocalDate toDateStr);
}
