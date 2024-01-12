package org.example.type_file;

import org.example.workstation.Computer;
import org.example.workstation.Program;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class FileEnum implements Computers {
    private Elements rowsTable;

    public FileEnum(Elements rowsTable) {
        this.rowsTable = rowsTable;
    }

    @Override
    public List<Computer> getComputersWithInstalledPrograms() {
        List<Computer> list = new ArrayList<>();
        Computer computer = new Computer();
        for (Element element : rowsTable) {
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
            computer.addProgramOnTheComputer(program);
        }
        return list;
    }
}