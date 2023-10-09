package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceFilesParser {
    private final String pathFile;
    private final String charsetName;

    public SourceFilesParser(String pathFile, String charsetName) {
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

    public List<String[]> getRowsFromFileEnum() {
        Elements elements = getDocumentFromFile().select("div").select("p");
        return getNamesPrograms(elements);
    }

    private Document getDocumentFromFile() {
        File input = new File(pathFile);
        Document doc;
        try {
            doc = Jsoup.parse(input, charsetName);
//            doc = Jsoup.parse(input);
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
                listPrograms.add(new String[]{computerName, programName, programVersion, manufacture});
            }
        }
        return listPrograms;
    }

    private List<String[]> getInfoAboutProgramsFromScripts(Elements rowsTable) {
        List<String[]> listPrograms = new ArrayList<>();

        int countTable = rowsTable.size();
        Elements elements = rowsTable.get(countTable - 1).select("tr");
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

    private List<Computer> getInfoAboutComputersFromScripts(Elements rowsTable) {
        List<Computer> computerList = new ArrayList<>();
        int countTable = rowsTable.size();
        Elements elements = rowsTable.get(countTable - 2).select("tr");
        for (Element row : elements) {
            computerList.add(getComputerFromScripts(row));
        }
        return computerList;
    }

    private Computer getComputerFromScripts(Element row) {
        Elements elements = row.select("td");
        Computer computer = new Computer();
        computer.setName(elements.get(0).text());
        computer.setNameOS(elements.get(1).text());
        computer.setVersionOS(elements.get(2).text());
        return computer;
    }

    private List<String[]> getNamesPrograms(Elements rowTable) {
        List<String[]> list = new ArrayList<>();
        String computer = "";
        for (Element element : rowTable) {
            String text = element.text();
            if (text.length() == 0) {
                continue;
            }
            if (text.indexOf("УСТАНОВЛЕННОЕ НА ") == 0) {
                computer = text.substring("УСТАНОВЛЕННОЕ НА ".length(), text.lastIndexOf(" "));
                continue;
            }

            if (text.indexOf("Дата_") == 0) {
                break;
            }
            text = text.substring(0, text.length() - 1);
            list.add(new String[]{text, computer});
        }
        return list;
    }
}