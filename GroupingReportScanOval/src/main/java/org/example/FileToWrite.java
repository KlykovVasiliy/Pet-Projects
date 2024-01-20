package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileToWrite {

    private FileToWrite() {
    }

    public static void write(String fileName, XWPFDocument document) {
        int indexFormatFile = fileName.indexOf(".html");
        fileName = fileName.substring(0, indexFormatFile).concat(".docx");
        File file = new File(fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
