package org.example;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FileFilterHTML extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".html");
    }

    @Override
    public String getDescription() {
        return "Текстовые файлы (*.html)";
    }
}
