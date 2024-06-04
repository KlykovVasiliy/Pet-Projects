package org.example;

import org.example.config.ApplicationConfig;
import org.example.model.Program;
import org.example.services.ProgramService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;


public class Main {
    public static void main(String[] args) throws IOException {
//        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//        var programService = context.getBean(ProgramService.class);
//        programService.setPathRegistry(PathsInTheRegistry.PROGRAM_FILES);
//        programService.setPathRegistry(PathsInTheRegistry.PROGRAM_FILES_X64);
//
//        programService.getListPrograms().stream()
//                        .sorted(Comparator.comparing(Program::getDisplayName))
//                                .forEach(System.out::println);
//
//        System.out.println(programService.getListPrograms().size());

        Process process = Runtime.getRuntime().exec("systeminfo");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
