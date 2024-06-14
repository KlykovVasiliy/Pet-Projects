package org.example;

public enum HtmlTags {
    HTML_START("<!DOCTYPE html>\n" +
            "<html lang=\"ru\">\n"),
    HEAD("<head>\n" +
            "\t<meta charset=\"utf-8\">\n" +
            "\t<meta name=\"viewpoint\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "\t<title>Сведения об операционной системе</title>\n" +
            "</head>\n"),
    BODY_START("<body>\n" +
            "\t<tr>Дата создания отчёта:</tr>\n"),
    HEADING_ABOUT_SYSTEM("\t<h2>Сведения об операционной системе</h2>\n"),
    TABLE_START("\t<table>\n"),
    TABLE_END("\t</table>\n"),
    HEADINGS_TABLE_ABOUT_SYSTEM("\t\t<tr>\n" +
            "\t\t\t<th>Компьютер</th>\n" +
            "\t\t\t<th>Название ОС</th>\n" +
            "\t\t\t<th>Версия ОС</th>\n" +
            "\t\t\t<th>Изготовитель ОС</th>\n" +
            "\t\t\t<th>Дата установки</th>\n" +
            "\t\t\t<th>Время загрузки системы</th>\n" +
            "\t\t\t<th>Тип системы</th>\n" +
            "\t\t\t<th>Домен</th>\n" +
            "\t\t\t<th>Сервер входа в сеть</th>\n" +
            "\t\t\t<th>Исправления</th>\n" +
            "\t\t</tr>\n"),
    HEADING_ABOUT_PROGRAMS("\t<h2>Установленное программное обеспечение</h2>\n"),
    HEADINGS_TABLE_ABOUT_PROGRAMS("\t\t<tr>\n" +
            "\t\t\t<th>№</th>\n" +
            "\t\t\t<th>Наименование</th>\n" +
            "\t\t\t<th>Версия</th>\n" +
            "\t\t\t<th>Производитель</th>\n" +
            "\t\t\t<th>Локализация</th>\n" +
            "\t\t\t<th>Путь к каталогу установки</th>\n" +
            "\t\t</tr>\n"),
    BODY_END("</body>\n"),
    HTML_END("</html>\n"),
    STYLE("<style type=\"text/css\">\n" +
            "\ttable {\n" +
            "\t\tborder: 1px solid grey;\n" +
            "\t}\n" +
            "\tth {\n" +
            "   \t\tborder: 1px solid grey;\n" +
            "\t}\n" +
            "\ttd {\n" +
            "   \t\tborder: 1px solid grey;\n" +
            "\t}\n" +
            "</style>");

    private final String tag;

    HtmlTags(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
