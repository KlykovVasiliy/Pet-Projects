package org.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void fillInInformationAboutTheWorkStation(Elements infoAboutOS, Elements rowsPrograms) {
        setInfoAboutComputer(infoAboutOS);
        addProgramsToComputer(rowsPrograms);
    }

    private void addProgramsToComputer (Elements rowsTable) {
        for(Element row : rowsTable) {
            Elements cells = row.select("td");

            Program program = new Program();
            if (cells.hasClass("s12")) {
                program.setManufacture(cells.get(1).text());
                program.setName(cells.get(2).text());
                program.setVersion(cells.get(3).text());
                programList.add(program);
            }
        }
    }

    private void setInfoAboutComputer(Elements rowsTable) {
        for(Element row : rowsTable) {
            Elements cells = row.select("td");
            if(cells.hasClass("s10")) {
                name = cells.get(0).text();
                nameOS = cells.get(1).text();
                programList = new ArrayList<>();
            }
        }
    }

    @Override
    public String toString() {
        return name + " - " + nameOS + "\n" + programList;
    }
}
