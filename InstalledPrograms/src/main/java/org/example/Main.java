package org.example;

import org.example.config.ApplicationConfig;
import org.example.model.Program;
import org.example.services.ProgramService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Comparator;


public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var programService = context.getBean(ProgramService.class);
        programService.setPathRegistry(PathsInTheRegistry.PROGRAM_FILES);
        programService.setPathRegistry(PathsInTheRegistry.PROGRAM_FILES_X64);

        programService.getListPrograms().stream()
                        .sorted(Comparator.comparing(Program::getDisplayName))
                                .forEach(System.out::println);

        System.out.println(programService.getListPrograms().size());
    }
}
