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
        String programName = getDoubleQuotesFromQuotes(program.getName());
        String manufacture = getDoubleQuotesFromQuotes(program.getManufacture());
        program.setName(programName);
        program.setManufacture(manufacture);
        programSet.add(program);
    }

    private String getDoubleQuotesFromQuotes(String text) {
        if (text == null) {
            return "";
        }
        text = text.replaceAll(String.valueOf((char) 171), "\"");
        text = text.replaceAll(String.valueOf((char) 187), "\"");
        text = text.replaceAll(String.valueOf((char) 8220), "\"");
        text = text.replaceAll(String.valueOf((char) 8221), "\"");
        return text.replaceAll("'", "\"");
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