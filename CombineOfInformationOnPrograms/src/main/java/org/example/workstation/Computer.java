package org.example.workstation;

import java.util.*;

public class Computer implements Comparable<Computer> {
    private String name;
    private OperationSystem operationSystem;
    private Set<Program> programSet;

    public Computer() {
        programSet = new TreeSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OperationSystem getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(OperationSystem operationSystem) {
        this.operationSystem = operationSystem;
    }

    public Set<Program> getProgramSet() {
        return programSet;
    }

    public void addProgramOnTheComputer(Program program) {
        programSet.add(program);
    }

    @Override
    public String toString() {
        return name + " - " + operationSystem + "\n" + programSet;
    }

    @Override
    public int hashCode() {
        return 31 * Integer.hashCode(name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Computer)) {
            return false;
        }
        Computer computer = (Computer) obj;
        return computer.getName().equals(this.name);
    }

    @Override
    public int compareTo(Computer o) {
        return Comparator.comparing(Computer::getName)
                .compare(this, o);
    }
}