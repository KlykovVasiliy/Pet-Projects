package org.example;

import java.util.List;

public class Computer {
    private String name;
    private String nameOS;
    private String versionOS;
    private List<Program> programList;

    public Computer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOS() {
        return nameOS;
    }

    public void setNameOS(String nameOS) {
        this.nameOS = nameOS;
    }

    public String getVersionOS() {
        return versionOS;
    }

    public void setVersionOS(String versionOS) {
        this.versionOS = versionOS;
    }

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    @Override
    public String toString() {
        return name + " - " + nameOS + "\n" + programList;
    }
}