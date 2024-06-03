package com.example.model;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Computer {
    private String name;
    private OperationSystem operationSystem;
    private final Set<Program> programSet;

    public Computer() {
        programSet = new TreeSet<>(
                Comparator.comparing(Program::getName)
                        .thenComparing(Program::getVersion)
        );
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

    public void addProgram(Program program) {
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
}