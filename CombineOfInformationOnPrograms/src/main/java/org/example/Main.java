package org.example;

import org.example.type_file.Computers;
import org.example.type_file.FileEnum;
import org.example.type_file.FileScript;
import org.example.type_file.FileTenStrike;
import org.example.workstation.Repository;
import org.jsoup.select.Elements;

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
    public static void main(String[] args) {
        String sourceDirectoryProgramsTeenStrike = "10/Programs/";
        String sourceDirectoryComputersTeenStrike = "10/Computers/";
        String sourceDirectoryEnum = "Enum/";
        String sourceDirectoryScripts = "Scripts/";

        //--------------------------------------
        Repository repository = new Repository();
        List<Elements> tenStrikePrograms = new ArrayList<>();
        List<Elements> tenStrikeComputers = new ArrayList<>();
        for (String file : getListFiles(sourceDirectoryProgramsTeenStrike)) {
            tenStrikePrograms.add(new SourceFilesParser(file, "UTF-8").getRowsFromFileTenStrike());
        }
        for (String file : getListFiles(sourceDirectoryComputersTeenStrike)) {
            tenStrikeComputers.add(new SourceFilesParser(file, "UTF-8").getRowsFromFileTenStrike());
        }
        Computers computers = new FileTenStrike(tenStrikePrograms, tenStrikeComputers);
        repository.addListComputers(computers.getComputersWithInstalledPrograms());

        //--------------------------------------
        for (String file : getListFiles(sourceDirectoryEnum)) {
            SourceFilesParser sourceFilesParser = new SourceFilesParser(file, "windows-1251");
            computers = new FileEnum(sourceFilesParser.getRowsFromFileEnum());
            repository.addListComputers(computers.getComputersWithInstalledPrograms());
        }

        //--------------------------------------
        for (String file : getListFiles(sourceDirectoryScripts)) {
            SourceFilesParser sourceFilesParser = new SourceFilesParser(file, "windows-1251");
            computers = new FileScript(sourceFilesParser.getRowFromFileScripts());
            repository.addListComputers(computers.getComputersWithInstalledPrograms());
        }
        //--------------------------------------
//        repository.printComputersWithPrograms();
//        repository.getListAllPrograms().forEach(System.out::println);

        DocumentDocx.writeProgramsForEachComputer(repository.getComputerList());
        DocumentDocx.writeAllProgramsWithLocalization(repository.getComputerList());
        DocumentDocx.writeAllProgramsInTableWithNameVersionManufacture(repository.getListAllPrograms());
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
}