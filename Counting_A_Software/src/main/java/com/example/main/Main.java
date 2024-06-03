package com.example.main;

import com.example.config.ProjectConfig;
import com.example.parser_files.ComputerScientist;
import com.example.services.ComputerService;
import com.example.working_with_files.Directory;
import com.example.working_with_files.DocumentDocx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private final static String DIRECTORY_PROGRAMS_TEEN_STRIKE = "src/main/resources/10/Programs/";
    private final static String DIRECTORY_COMPUTERS_TEEN_STRIKE = "src/main/resources/10/Computers/";
    private final static String DIRECTORY_ENUM = "src/main/resources/Enum/";
    private final static String DIRECTORY_SCRIPTS = "src/main/resources/Scripts/";

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var computerService = context.getBean(ComputerService.class);
        var documentDocx = context.getBean(DocumentDocx.class);

        ComputerScientist computerScientist;
        for (String file : Directory.getListFiles(DIRECTORY_PROGRAMS_TEEN_STRIKE)) {
            computerScientist = context.getBean("fileTenStrikeProgram", ComputerScientist.class);
            computerScientist.setPathFileAndCharset(file, "UTF-8");
            computerService.addComputerList(computerScientist.getComputers());
        }
        for (String file : Directory.getListFiles(DIRECTORY_COMPUTERS_TEEN_STRIKE)) {
            computerScientist = context.getBean("fileTenStrikeOs", ComputerScientist.class);
            computerScientist.setPathFileAndCharset(file, "UTF-8");
            computerService.addComputerList(computerScientist.getComputers());
        }

        //--------------------------------------
        for (String file : Directory.getListFiles(DIRECTORY_ENUM)) {
            computerScientist = context.getBean("fileEnum", ComputerScientist.class);
            computerScientist.setPathFileAndCharset(file, "windows-1251");
            computerService.addComputerList(computerScientist.getComputers());
        }

        //--------------------------------------
        for (String file : Directory.getListFiles(DIRECTORY_SCRIPTS)) {
            computerScientist = context.getBean("fileScript", ComputerScientist.class);
            computerScientist.setPathFileAndCharset(file, "windows-1251");
            computerService.addComputerList(computerScientist.getComputers());
        }
        //--------------------------------------
        computerService.printComputersWithPrograms();
        computerService.getListPrograms().forEach(System.out::println);

        documentDocx.writeProgramsForEachComputer(computerService
                .getComputerList());
        documentDocx.writeAllProgramsWithLocalization(computerService
                .getComputerList());
        documentDocx.writeAllProgramsInTableWithNameVersionManufacture(computerService
                .getListPrograms());
    }
}
