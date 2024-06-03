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
import java.util.Map;
import java.util.TreeMap;

@Component
@Qualifier(value = "fileTenStrikeProgram")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileTenStrikeProgram implements ComputerScientist {
    private final Map<String, Computer> mapComputers;

    private final ReaderTheDocument readerTheDocument;

    @Autowired
    public FileTenStrikeProgram(ReaderTheDocument readerTheDocument) {
        this.readerTheDocument = readerTheDocument;
        mapComputers = new TreeMap<>();
    }

    public Elements getRowsFromFile() {
        return readerTheDocument.getDocumentFromFile().select("table").select("tr");
    }

    @Override
    public List<Computer> getComputers() {
        for (Element row : getRowsFromFile()) {
            Elements cells = row.select("td");
            if (!cells.hasClass("s11")) {
                continue;
            }
            String computerName = cells.get(4).text().toUpperCase();
            String manufacture = cells.get(1).text();
            String programName = cells.get(2).text();
            String programVersion = cells.get(3).text();
            Program program = new Program(programName, programVersion, manufacture);
            addByOneProgramToMapComputers(computerName, program);
        }
        return new ArrayList<>(mapComputers.values());
    }

    @Override
    public void setPathFileAndCharset(String pathFile, String charsetName) {
        readerTheDocument.setPathFileAndCharset(pathFile, charsetName);
    }

    private void addByOneProgramToMapComputers(String computer, Program program) {
        String[] computers;
        if (computer.contains(" ")) {
            computers = computer.split(" ");
        } else {
            computers = new String[]{computer};
        }
        for (String str : computers) {
            Computer pc;
            if (mapComputers.containsKey(str)) {
                pc = mapComputers.get(str);
            } else {
                pc = new Computer();
                pc.setName(str);
            }
            pc.addProgram(program);
            /*
                Для чего применяется следующая строка кода если была получена ссылка на объект
                Computer из mapComputers?
             */
            mapComputers.put(str, pc);
        }
    }
}
