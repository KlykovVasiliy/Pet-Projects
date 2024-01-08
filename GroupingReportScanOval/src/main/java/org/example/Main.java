package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String workDirectory = Paths.get("").toAbsolutePath().toString();
        String srcDir = workDirectory.concat("/sourceFiles/");
        String outDir = workDirectory.concat("/ourFiles/");

        for (String file : getListFiles(srcDir)) {
            SourceFilesParser parser = new SourceFilesParser();
            parser.readDataFromTableBDU(parser.getElementsTable(srcDir.concat(file)));
            Map<String, Vulnerabilitie> vulnerabilitieMap = new TreeMap<>(parser.getVulnerabilitieMap());

            DocumentDocx documentDocx = new DocumentDocx();
            XWPFDocument document = documentDocx.getDocument(vulnerabilitieMap);
            writeToFile(outDir.concat(file), document);
        }
    }

    public static Set<String> getListFiles(String dir) {
        Pattern pattern = Pattern.compile("\\.html$");

        Set<String> setFiles;
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            setFiles = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> {
                        Matcher matcher = pattern.matcher(name);
                        return matcher.find();
                    })
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return setFiles;
    }

    public static void writeToFile(String fileName, XWPFDocument document) {
        fileName = renameFileForWrite(fileName);
        File file = new File(fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String renameFileForWrite(String fileName) {
        String typeFile = ".html";
        int indexFormatFile = fileName.indexOf(typeFile);
        return fileName.substring(0, indexFormatFile).concat(".docx");
    }
}