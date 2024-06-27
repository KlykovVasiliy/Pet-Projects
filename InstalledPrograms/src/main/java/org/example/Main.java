package org.example;

import org.example.config.ApplicationConfig;
import org.example.services.ComputerService;
import org.example.writers.WriterDocument;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        var computerService = context.getBean(ComputerService.class);
        var computer = computerService.getComputer();

        var writeToHtml = context.getBean("writeToHtml", WriterDocument.class);
        var writeToJson = context.getBean("writeToJson", WriterDocument.class);
        writeToHtml.writerToDocument(computer);
        writeToJson.writerToDocument(computer);
    }
}
