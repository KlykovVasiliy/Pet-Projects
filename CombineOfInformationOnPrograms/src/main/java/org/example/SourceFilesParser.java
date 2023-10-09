package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserFileHTML {
    private final String pathFile;
    private final String charsetName;

    public ParserFileHTML(String pathFile, String charsetName) {
        this.pathFile = pathFile;
        this.charsetName = charsetName;
    }

    public List<String[]> getRowsFromFileTeenStrike() {
        Elements elements = getDocumentFromFile().select("table").first().select("tr");
        return getInfoAboutProgramsFromTeenStrike(elements);
    }

    public List<String[]> getRowFromFileScripts() {
        Elements elements = getDocumentFromFile().select("table");
        return getInfoAboutProgramsFromScripts(elements);
    }

    private Document getDocumentFromFile() {
        File input = new File(pathFile);
        Document doc;
        try {
            doc = Jsoup.parse(input, charsetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    private List<String[]> getInfoAboutProgramsFromTeenStrike(Elements rowsTable) {
        List<String[]> listPrograms = new ArrayList<>();
        for (Element row : rowsTable) {
            Elements cells = row.select("td");

            if (cells.hasClass("s12")) {
                String manufacture = cells.get(1).text();
                String programName = cells.get(2).text();
                String programVersion = cells.get(3).text();
                String computerName = cells.get(4).text();
                //--------другая таблица детальная информация
//                String manufacture = cells.get(4).text();
//                String programName = cells.get(1).text();
//                String programVersion = cells.get(5).text();
//                String computerName = "WIN-N0PJJN50CVQ";
                //--------
                listPrograms.add(new String[]{computerName, programName, programVersion, manufacture});
            }
        }
        return listPrograms;
    }

    private List<String[]> getInfoAboutProgramsFromScripts(Elements rowsTable) {
        List<String[]> listPrograms = new ArrayList<>();

//        Elements elements = rowsTable.get(2).select("tr");                    //домен
        Elements elements = rowsTable.get(1).select("tr");            //локальная станция
        for (Element row : elements) {
            Elements cells = row.select("td");
            if (cells.size() == 0) {
                continue;
            }

            String programName = cells.get(0).text();
            String programVersion = cells.get(1).text();
            String manufacture = cells.get(2).text();
            String computerName = cells.get(3).text();
            listPrograms.add(new String[]{computerName, programName, programVersion, manufacture});
        }
        return listPrograms;
    }
//    public Elements getRowsFromFileEnum() {
//
//    }
}