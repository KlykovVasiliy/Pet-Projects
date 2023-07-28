package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class ParserFileHTML {
    private final String pathFile;
    private final String charsetName;
    private final Document document;

    public ParserFileHTML(String pathFile, String charsetName) {
        this.pathFile = pathFile;
        this.charsetName = charsetName;
        document = getDocumentFromFile(pathFile, charsetName);
    }

    private Document getDocumentFromFile(String pathFile, String charsetName) {
        File input = new File(pathFile);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, charsetName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    public Elements getRowsFromFileTeenStrike() {
        return document.select("table").first().select("tr");
    }

//    public Elements getRowsFromFileEnum() {
//
//    }

    public Elements getRowFromFileScripts() {
        return document.select("table");
    }
}

