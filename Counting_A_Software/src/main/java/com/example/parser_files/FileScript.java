package com.example.parser_files;

import com.example.model.Computer;
import com.example.model.OperationSystem;
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
@Qualifier(value = "fileScript")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FileScript implements ComputerScientist {
    private Map<String, Computer> mapComputers;

    private final ReaderTheDocument readerTheDocument;
    private Elements tables;

    @Autowired
    public FileScript(ReaderTheDocument readerTheDocument) {
        this.readerTheDocument = readerTheDocument;
        mapComputers = new TreeMap<>();
    }

    private Elements getTablesFromFile() {
        return readerTheDocument.getDocumentFromFile().select("table");
    }

    @Override
    public List<Computer> getComputers() {
        tables = getTablesFromFile();
        Elements tablePrograms = tables.get(tables.size() - 1).select("tr");
        for (Element row : tablePrograms) {
            Elements cells = row.select("td");
            if (cells.isEmpty()) {
                continue;
            }
            String programName = cells.get(0).text();
            if (programName.equalsIgnoreCase("Название")) {
                continue;
            }
            String programVersion = cells.get(1).text();
            String manufacture = cells.get(2).text();

            Program program = new Program(programName, programVersion, manufacture);
            String computerName = cells.get(3).text().toUpperCase();
            addByOneProgramToMapComputers(computerName, program);
        }
        addInfoAboutOS();
        return new ArrayList<>(mapComputers.values());
    }

    @Override
    public void setPathFileAndCharset(String pathFile, String charsetName) {
        readerTheDocument.setPathFileAndCharset(pathFile, charsetName);
    }

    private void addByOneProgramToMapComputers(String computer, Program program) {
        String[] computers;
        if (computer.contains(", ")) {
            computers = computer.split(", ");
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

    private void addInfoAboutOS() {
        for (Computer computer : getComputersFromTableComputers()) {
            String nameComputer = computer.getName();
            if (mapComputers.containsKey(nameComputer)) {
                mapComputers.get(nameComputer).setOperationSystem(computer.getOperationSystem());
            }
        }
    }

    private List<Computer> getComputersFromTableComputers() {
        List<Computer> computerList = new ArrayList<>();
        Elements computers = tables.get(tables.size() - 2).select("tr");
        for (Element row : computers) {
            Computer computer = new Computer();
            Elements elements = row.select("td");
            if (elements.isEmpty()) {
                continue;
            }
            String name = elements.get(1).text();
            String version = elements.get(2).text();
            computer.setName(elements.get(0).text());
            computer.setOperationSystem(new OperationSystem(name, version));
            computerList.add(computer);
        }
        return computerList;
    }
}
