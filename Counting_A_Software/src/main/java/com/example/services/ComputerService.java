package com.example.services;

import com.example.model.Computer;
import com.example.model.Program;
import com.example.parser_files.ComputerScientist;
import com.example.repositories.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ComputerService {
    private final ComputerRepository computerRepository;

    private ComputerScientist computerScientist;

    @Autowired
    public ComputerService(ComputerRepository computerRepository) {
        this.computerRepository = computerRepository;
    }

    public void addComputer(Computer computer) {
        if (computerRepository.getMapComputers().containsKey(computer.getName())) {
            Computer computerFromRepository = computerRepository.getMapComputers()
                    .get(computer.getName());
            for (Program program : computer.getProgramSet()) {
                computerFromRepository.addProgram(program);
            }
            if (computerFromRepository.getOperationSystem() == null) {
                computerFromRepository.setOperationSystem(computer.getOperationSystem());
            }
        } else {
            computerRepository.addComputer(computer);
        }
    }

    public void addComputerList(List<Computer> list) {
        for (Computer computer : list) {
            addComputer(computer);
        }
    }

    public List<String> getListComputersNames() {
        return computerRepository.getMapComputers().values().stream()
                .map(Computer::getName)
                .toList();
    }

    public List<Computer> getComputerList() {
        return computerRepository.getMapComputers().values().stream().toList();
    }

    public List<Program> getListPrograms() {
        return computerRepository.getMapComputers().values().stream()
                .flatMap(computer -> computer.getProgramSet().stream())
                .sorted(Comparator.comparing(Program::getName).thenComparing(Program::getVersion))
                .distinct()
                .toList();
    }

    public List<String> getListProgramsNames() {
        return computerRepository.getMapComputers().values().stream()
                .flatMap(computer -> computer.getProgramSet().stream())
                .map(Program::getName)
                .distinct()
                .toList();
    }

    public void printComputersWithPrograms() {
        List<Computer> list = computerRepository.getMapComputers().values().stream()
                .sorted(Comparator.comparing(Computer::getName)).toList();
        list.forEach(
                c -> {
                    System.out.printf("%s - %s%n", c.getName(), c.getOperationSystem());
                    c.getProgramSet().forEach(p ->
                            System.out.printf("%s - %s - %s%n", p.getName(), p.getVersion(),
                                    p.getManufacture()));
                    System.out.printf("%s %d%n%n", "Количество установленных программ:",
                            c.getProgramSet().size());
                }
        );
    }
}
