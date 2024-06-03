package com.example.parser_files;

import com.example.model.Computer;
import com.example.model.Program;
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
@Qualifier(value = "fileEnum")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileEnum implements ComputerScientist {
    private final ReaderTheDocument readerTheDocument;

    @Autowired
    public FileEnum(ReaderTheDocument readerTheDocument) {
        this.readerTheDocument = readerTheDocument;
    }

    private Elements getRowsFromFile() {
        return readerTheDocument.getDocumentFromFile().select("div").select("p");
    }


    @Override
    public List<Computer> getComputers() {
        List<Computer> list = new ArrayList<>();
        Computer computer = new Computer();
        for (Element element : getRowsFromFile()) {
            String text = element.text();
            if (text.length() <= 1) {
                continue;
            }
            if (text.indexOf("УСТАНОВЛЕННОЕ НА ") == 0) {
                String nameComputer =
                        text.substring("УСТАНОВЛЕННОЕ НА ".length(), text.lastIndexOf(" "));
                nameComputer = nameComputer.contains(" x64") ? nameComputer.substring(0,
                        nameComputer.indexOf(" x64")) : nameComputer;
                computer = new Computer();
                computer.setName(nameComputer.toUpperCase());
                list.add(computer);
                continue;
            }

            if (text.indexOf("Дата_") == 0) {
                break;
            }
            text = text.substring(0, text.length() - 1);
            Program program = new Program(text, "");
            computer.addProgram(program);
        }
        return list;
    }

    @Override
    public void setPathFileAndCharset(String pathFile, String charsetName) {
        readerTheDocument.setPathFileAndCharset(pathFile, charsetName);
    }
}
