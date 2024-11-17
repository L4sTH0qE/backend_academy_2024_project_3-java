package backend.academy.writer;

import backend.academy.model.LogReport;

/// Интерфейс для классов, реализующих способ генерации файла заданного формата с собранной по логам статистикой.
public interface LogWriter {

    // Используемый для отчета перцентиль - 95-ый.
    int PERCENTILE = 95;

    /// Метод для создания файла с собранной по логам статистикой.
    void generateFile(LogReport logReport);
}
