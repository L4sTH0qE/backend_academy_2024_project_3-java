package backend.academy.model;

import lombok.Getter;

/// Класс-перечисление для расширения файла изображения.
@Getter public enum FileFormat {
    MARKDOWN("markdown"),
    ADOC("adoc");

    // Расширение файла.
    private final String formatName;

    // Инициализируем расширение файла.
    FileFormat(String formatName) {
        this.formatName = formatName;
    }
}
