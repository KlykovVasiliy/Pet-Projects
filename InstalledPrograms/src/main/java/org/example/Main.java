package org.example;

import org.example.config.ApplicationConfig;
import org.example.services.ComputerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var computerService = context.getBean(ComputerService.class);

        System.out.println(computerService.getInformationAboutSystem());
        System.out.println("\n");
        computerService.getProgramList().forEach(System.out::println);
        System.out.println("Количество установленных программ на рабочей станции: " +
                computerService.getProgramList().size());
    }
}
