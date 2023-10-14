package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.example.workstation.Computer;
import org.example.workstation.Program;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DocumentDocx {

    private DocumentDocx() {
    }

    public static void writeAllProgramsInTableWithNameVersionManufacture(List<Program> programList) {
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFTable table = doc.createTable();

            XWPFTableRow row0 = table.getRow(0);
            row0.getCell(0).setText("№");
            row0.addNewTableCell().setText("Наименование программного продукта");
            row0.addNewTableCell().setText("Версия");
            row0.addNewTableCell().setText("Разработчик");

            for (int i = 0; i < programList.size(); i++) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(i + 1));
                row.getCell(1).setText(programList.get(i).getName());
                row.getCell(2).setText(programList.get(i).getVersion());
                row.getCell(3).setText(programList.get(i).getManufacture());
            }
            try (FileOutputStream out = new FileOutputStream(
                    "src/main/resources/resultDirectory/Общий список программ в таблице.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeProgramsForEachComputer(List<Computer> computerList) {
        //добавить столбец локализация
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFTable table = doc.createTable();

            XWPFTableRow row0 = table.getRow(0);
            row0.getCell(0).setText("№");
            row0.addNewTableCell().setText("Компьютер");
            row0.addNewTableCell().setText("Описание");

            for (int i = 0; i < computerList.size(); i++) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(i + 1));
                row.getCell(1).setText(computerList.get(i).getName());
                StringBuilder builder = new StringBuilder();
                for (Program program : computerList.get(i).getProgramSet()) {
                    builder.append(program.getName()).append(";")
                            .append(System.getProperty("line.separator"));
                }
                row.getCell(2).setText(builder.toString());
            }
            try (FileOutputStream out = new FileOutputStream(
                    "src/main/resources/resultDirectory/Список программ по каждому компьютеру.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}