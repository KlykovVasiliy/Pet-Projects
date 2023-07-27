package org.example;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Main {
    public static void main(String[] args) {

        String pathFileOS = "src/main/resources/10/os/270623_OR02_Операционная система (подробно).html";
        String pathFile = "src/main/resources/10/programs/270623_OR02_Все установленные приложениям (с компьютерами).html";

        ParserFileHTML parserFileHTML = new ParserFileHTML(pathFileOS, "UTF-8");
        Elements infoAboutOS = parserFileHTML.getRowsFromFileTeenStrike();

        parserFileHTML = new ParserFileHTML(pathFile, "UTF-8");
        Elements rowsPrograms = parserFileHTML.getRowsFromFileTeenStrike();

        Computer computer = new Computer();
        computer.fillInInformationAboutTheWorkStation(infoAboutOS, rowsPrograms);

        for (Program program : computer.getProgramList()) {
            System.out.println(program);
        }




    }
}