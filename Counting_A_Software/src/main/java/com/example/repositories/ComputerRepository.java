package com.example.repositories;

import com.example.model.Computer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.TreeMap;

@Repository
public class ComputerRepository {

    private final Map<String, Computer> mapComputers;

    public ComputerRepository() {
        this.mapComputers = new TreeMap<>();
    }

    public Map<String, Computer> getMapComputers() {
        return mapComputers;
    }

    public void addComputer(Computer computer) {
        mapComputers.put(computer.getName(), computer);
    }

}
