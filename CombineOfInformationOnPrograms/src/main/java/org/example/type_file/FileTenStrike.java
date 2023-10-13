package org.example.type_file;

import org.example.workstation.Computer;
import org.example.workstation.OperationSystem;
import org.example.workstation.Program;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileTenStrike {
    private Map<String, Computer> mapComputers;
    private List<Elements> programElements;
    private List<Elements> computerElements;

    public FileTenStrike(List<Elements> programElements, List<Elements> computerElements) {
        this.programElements = programElements;
        this.computerElements = computerElements;
        mapComputers = new TreeMap<>();
    }

    public List<Computer> getComputersWithInstalledPrograms() {
        for (Elements rowsTable : programElements) {
            for (Element row : rowsTable) {
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
        }
        addInfoAboutOS();
        return new ArrayList<>(mapComputers.values());
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
            pc.addProgramOnTheComputer(program);
            mapComputers.put(str, pc);
        }
    }

    private List<Computer> getComputersWithOperationSystem() {
        List<Computer> computerList = new ArrayList<>();
        for (Elements rowsTable : computerElements) {
            for (Element row : rowsTable) {
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
        }
        return computerList;
    }

    private void addInfoAboutOS() {
        List<Computer> list = getComputersWithOperationSystem();
        for (Computer computer : list) {
            if (mapComputers.containsKey(computer.getName())) {
                mapComputers.get(computer.getName()).setOperationSystem(computer.getOperationSystem());
            }
        }
    }
}