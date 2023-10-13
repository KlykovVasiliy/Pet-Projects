package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class SourceFilesParser {
    private final String pathFile;
    private final String charsetName;

    public SourceFilesParser(String pathFile, String charsetName) {
        this.pathFile = pathFile;
        this.charsetName = charsetName;
    }

    public Elements getRowsFromFileTenStrike() {
        return getDocumentFromFile().select("table").select("tr");
    }

    public Elements getRowsFromFileEnum() {
        return getDocumentFromFile().select("div").select("p");
    }

    public Elements getRowFromFileScripts() {
        return getDocumentFromFile().select("table");
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
}