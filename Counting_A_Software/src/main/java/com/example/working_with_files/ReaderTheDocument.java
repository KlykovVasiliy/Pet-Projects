package com.example.working_with_files;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(value = "readerTheDocument")
public class ReaderTheDocument {
    private String pathFile;
    private String charsetName;

    public void setPathFileAndCharset(String pathFile, String charsetName) {
        this.pathFile = pathFile;
        this.charsetName = charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }

    public Document getDocumentFromFile() {
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