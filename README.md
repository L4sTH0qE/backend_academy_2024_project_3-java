# Домашнее задание 3. Logs analyzer

Программа ожидает следующие cli-аргументы:
```
--path <url/glob pattern> [--from <yyyy-MM-dd>] [--to <yyyy-MM-dd>] [--format <markdown/adoc>]
```
Здесь:

`--path` - обязательный аргумент пути к файлу, в котором записаны логи для анализа. Может быть как локальным пути (есть поддержка glob-паттернов, так и URL).

`--from` - необязательный аргумент нижней границы временного диапазона логов для фильтрации (входит в диапазон).

`--to` - необязательный аргумент нижней границы временного диапазона логов для фильтрации (НЕ входит в диапазон).

`--format` - необязательный аргумент формата выходного файла: markdown или adoc (md или adoc соответственно). По умолчанию markdown.

Результат работы программы записывается в файл `results/result.*` с соответствующим форматом.
