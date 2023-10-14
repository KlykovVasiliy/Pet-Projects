package org.example.type_file;

import org.example.workstation.Computer;
import org.example.workstation.OperationSystem;
import org.example.workstation.Program;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

public class FileScript implements Computers {
    private Map<String, Computer> mapComputers;
    private Elements tables;

    public FileScript(Elements tables) {
        this.tables = tables;
        mapComputers = new TreeMap<>();
    }

    @Override
    public List<Computer> getComputersWithInstalledPrograms() {
        Elements tablePrograms = tables.get(tables.size() - 1).select("tr");
        for (Element row : tablePrograms) {
            Elements cells = row.select("td");
            if (cells.size() == 0) {
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
            pc.addProgramOnTheComputer(program);
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
            if (elements.size() == 0) {
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
