package org.example.workstation;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private String name;
    private List<Computer> computerList;

    public void addListComputers(List<Computer> list) {
        computerList.addAll(list);
        joinProgramsForEachComputer();
    }

    private void joinProgramsForEachComputer() {
        for (int i = 0; i < computerList.size(); i++) {
            for (int j = i + 1; j < computerList.size(); j++) {
                Computer pcFirst = computerList.get(i);
                Computer pcSecond = computerList.get(j);
                if (pcFirst.equals(pcSecond)) {
                    for (Program program : pcSecond.getProgramSet()) {
                        pcFirst.addProgramOnTheComputer(program);
                    }
                    if (pcFirst.getOperationSystem() == null) {
                        pcFirst.setOperationSystem(pcSecond.getOperationSystem());
                    }
                }
            }
        }
        computerList = new ArrayList<>(computerList.stream().sorted().distinct().toList());
    }

    public Repository() {
        computerList = new ArrayList<>();
    }

    public void printComputersWithPrograms() {
        List<Computer> list = computerList.stream().sorted().toList();
        for (Computer computer : list) {
            System.out.println(computer.getName());
            for (Program program : computer.getProgramSet()) {
                System.out.printf("%s - %s - %s%n", program.getName(), program.getVersion(),
                        program.getManufacture());
            }
            System.out.println("Количество установленных программ: " + computer.getProgramSet().size());
            System.out.println();
        }
    }

    public List<Computer> getComputerList() {
        return computerList;
    }

    public List<Program> getListAllPrograms() {
        return computerList.stream()
                .flatMap(computer -> computer.getProgramSet().stream())
                .sorted()
                .distinct()
                .toList();
    }

    public List<String> getOnlyNamesPrograms() {
        return computerList.stream()
                .flatMap(computer -> computer.getProgramSet().stream())
                .map(Program::getName)
                .distinct()
                .toList();
    }
}
