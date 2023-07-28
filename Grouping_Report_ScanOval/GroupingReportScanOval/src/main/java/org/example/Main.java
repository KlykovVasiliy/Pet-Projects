package org.example;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            Elements rowsTable = getElementsTable(srcDir.concat(file));
            Map<String, Vulnerabilitie> vulnerabilitieMap = new TreeMap<>(groupingByPrograms(rowsTable));

            DocumentExcel excel = new DocumentExcel();
            XSSFWorkbook document = excel.getDocument(vulnerabilitieMap.values());
            writeToFile(outDir.concat(file), document);
        }
    }

    private static Elements getElementsTable(String pathFile) {
        File file = new File(pathFile);
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements vulnerabilities = doc.getElementsByClass("vulnerabilities");
        Element table = vulnerabilities.select("table").first();
        return table.select("tr");
    }

    private static Map<String, Vulnerabilitie> groupingByPrograms(Elements rowsTable) {
        Map<String, Vulnerabilitie> vulnerabilitieMap = new HashMap<>();

        for (int index = 1; index < rowsTable.size(); index += 2) {
            String bdu = rowsTable.get(index).getElementsByClass("bdu").text();
            String keyProgram = rowsTable.get(index + 1).getElementsByClass("fileslist prods").text();
            String descProgram = rowsTable.get(index + 1).getElementsByClass("desc fileslist").text();
            List<String> keysList = getKeysFromText(keyProgram);
            List<String> descProgramList = getDescriptionsPrograms(descProgram);

            Vulnerabilitie vulnerabilitie;
            for (int indexKey = 0; indexKey < keysList.size(); indexKey++) {
                String key = keysList.get(indexKey);
                String description = "";
                description = descProgramList.get(descProgramList.size() < keysList.size() ? 0 : indexKey);

                if (vulnerabilitieMap.containsKey(key)) {
                    vulnerabilitie = vulnerabilitieMap.get(key);
                    vulnerabilitie.addToBduList(bdu);
                } else {
                    Set<String> set = new TreeSet<>();
                    set.add(bdu);
                    vulnerabilitie = new Vulnerabilitie(key, description, set);
                }
                vulnerabilitieMap.put(key, vulnerabilitie);
            }
        }
        return vulnerabilitieMap;
    }

    public static String getKeyWithoutPrefix(String key) {
        String prefixProgram = "cpe:/a:";
        String prefixOS = "cpe:/o:";
        if (key.length() > prefixProgram.length()) {
            key = key.replace(prefixProgram, "");
            key = key.replace(prefixOS, "");
        }
        return key;
    }

    public static List<String> getKeysFromText(String keyPrograms) {
        String key = getKeyWithoutPrefix(keyPrograms);
        String[] strings = key.split(" ");
        Arrays.sort(strings, String::compareTo);
        return Arrays.asList(strings);
    }

    public static List<String> getDescriptionsPrograms(String descProgram) {
        String[] strings = descProgram.split("C:");
        List<String> resultList = new ArrayList<>();
        for (String str : strings) {
            if (str.length() > 0) {
                resultList.add("C:".concat(str).strip());
            }
        }
        resultList.sort(String::compareTo);
        if (descProgram.length() == 0) {
            resultList.add("");
        }
        return resultList;
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

    public static void writeToFile(String fileName, XSSFWorkbook workbook) {
        fileName = renameFileForWrite(fileName);
        File file = new File(fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String renameFileForWrite(String fileName) {
        String typeFile = ".html";
        int indexFormatFile = fileName.indexOf(typeFile);
        return fileName.substring(0, indexFormatFile).concat(".xlsx");
    }
}