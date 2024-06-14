package org.example;

import org.example.config.ApplicationConfig;
import org.example.services.ComputerService;
import org.example.services.DocumentHtmlService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var computerService = context.getBean(ComputerService.class);
        var computer = computerService.getComputer();
        var documentHtmlService = context.getBean(DocumentHtmlService.class);

        documentHtmlService.setComputer(computer);
        documentHtmlService.WriteToDocument();
    }
}
