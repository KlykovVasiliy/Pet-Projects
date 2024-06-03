package com.example.parser_files;

import com.example.model.Computer;
import com.example.model.OperationSystem;
import com.example.working_with_files.ReaderTheDocument;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier(value = "fileTenStrikeOs")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileTenStrikeOs implements ComputerScientist {
    private final ReaderTheDocument readerTheDocument;

    @Autowired
    public FileTenStrikeOs(ReaderTheDocument readerTheDocument) {
        this.readerTheDocument = readerTheDocument;
    }

    private Elements getRowsFromFile() {
        return readerTheDocument.getDocumentFromFile().select("table").select("tr");
    }

    @Override
    public List<Computer> getComputers() {
        List<Computer> computerList = new ArrayList<>();
        for (Element row : getRowsFromFile()) {
            Computer computer = new Computer();
            Elements cells = row.select("td");
            if (!cells.hasClass("s9")) {
                continue;
            }
            computer.setName(cells.get(0).text().toUpperCase());
            String str = cells.get(1).text();
            String nameOS = str.substring(0, str.lastIndexOf(" "));
            String versionOS = str.substring(str.lastIndexOf(" "));
            computer.setOperationSystem(new OperationSystem(nameOS, versionOS));
            computerList.add(computer);
        }
        return computerList;
    }

    @Override
    public void setPathFileAndCharset(String pathFile, String charsetName) {
        readerTheDocument.setPathFileAndCharset(pathFile, charsetName);
    }
}
