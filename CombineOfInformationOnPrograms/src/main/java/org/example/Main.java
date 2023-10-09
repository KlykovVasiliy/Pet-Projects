package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    private static Map<String, Set<Program>> programMapForEachComputer = new TreeMap<>();

    public static void main(String[] args) {
        String sourceDirectoryTeenStrike = "10/";
        String sourceDirectoryEnum = "Enum/";
        String sourceDirectoryScripts = "Scripts/";

        for (String fileTeenStrike : getListFiles(sourceDirectoryTeenStrike)) {
            SourceFilesParser sourceFilesParser = new SourceFilesParser(fileTeenStrike, "UTF-8");
            addProgramsToMapComputers(sourceFilesParser.getRowsFromFileTeenStrike());
        }

        for (String fileScripts : getListFiles(sourceDirectoryScripts)) {
            SourceFilesParser sourceFilesParser = new SourceFilesParser(fileScripts, "windows-1251");
            addProgramsToMapComputers(sourceFilesParser.getRowFromFileScripts());
        }

        for (String fileEnum : getListFiles(sourceDirectoryEnum)) {
            SourceFilesParser sourceFilesParser = new SourceFilesParser(fileEnum, "windows-1251");
            addProgramsToMapComputers(sourceFilesParser.getRowsFromFileEnum());
        }

        printInfoAboutInstalledProgramsForEachComputer();

        for (Program program : getListAllPrograms()) {
            System.out.println(program.toString().concat(";"));
        }
        DocumentDocx.writeToFileDocx(getListAllPrograms());
    }

    private static void addProgramsToMapComputers(List<String[]> list) {
        for (String[] strings : list) {
            String nameProgram = "";
            String versionProgram = "";
            String manufacture = "";
            String nameComputer = "";
            if (strings.length == 4) {
                nameComputer = strings[0].toUpperCase();
                nameProgram = getDoubleQuotesFromQuotes(strings[1]);
                manufacture = getDoubleQuotesFromQuotes(strings[3]);
                versionProgram = strings[2];
            }
            if (strings.length == 2) {
                nameComputer = strings[1];
                nameProgram = strings[0];
            }
            Program program = new Program(nameProgram, versionProgram, manufacture);
            addByOneProgramToMap(nameComputer, program);
        }
    }

    private static void addByOneProgramToMap(String computer, Program program) {
        String[] computers;
        if (computer.contains(", ")) {
            computers = computer.split(", ");
        } else {
            computers = new String[]{computer};
        }
        for (String pc : computers) {
            Set<Program> programSet;
            if (programMapForEachComputer.containsKey(pc)) {
                programSet = programMapForEachComputer.get(pc);
            } else {
                programSet = new TreeSet<>();
            }
            programSet.add(program);
            programMapForEachComputer.put(pc, programSet);
        }
    }

    private static void printInfoAboutInstalledProgramsForEachComputer() {
        for (Map.Entry<String, Set<Program>> pair : programMapForEachComputer.entrySet()) {
            System.out.println("----------------------------------------------");
            System.out.println(pair.getKey());
            for (Program program : pair.getValue()) {
                System.out.println(program.getName().concat("; "));
            }
            System.out.println("Установлено программ: " + pair.getValue().size());
            System.out.println("----------------------------------------------");
        }
    }

    private static List<Program> getListAllPrograms() {
        List<Program> programList = new ArrayList<>();
        for (Map.Entry<String, Set<Program>> pair : programMapForEachComputer.entrySet()) {
            for (Program program : pair.getValue()) {
                programList.add(program);
            }
        }
        programList = programList.stream().sorted().distinct().toList();
        return programList;
    }


    public static Set<String> getListFiles(String dir) {
        Pattern pattern1 = Pattern.compile("\\.html$");
        Pattern pattern2 = Pattern.compile("\\.htm$");

        Set<String> setFiles;
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            setFiles = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(dir::concat)
                    .filter(name -> {
                        Matcher matcher1 = pattern1.matcher(name);
                        Matcher marcher2 = pattern2.matcher(name);
                        return matcher1.find() || marcher2.find();
                    })
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return setFiles;
    }

    private static String getDoubleQuotesFromQuotes(String text) {
        text = text.replaceAll(String.valueOf((char) 171), "\"");
        text = text.replaceAll(String.valueOf((char) 187), "\"");
        text = text.replaceAll(String.valueOf((char) 8220), "\"");
        text = text.replaceAll(String.valueOf((char) 8221), "\"");
        return text.replaceAll("'", "\"");
    }


//    private void setInfoAboutComputer(Elements rowsTable) {
//        for(Element row : rowsTable) {
//            Elements cells = row.select("td");
//            if(cells.hasClass("s10")) {
//                name = cells.get(0).text();
//                nameOS = cells.get(1).text();
//                programList = new ArrayList<>();
//            }
//        }
//    }
}