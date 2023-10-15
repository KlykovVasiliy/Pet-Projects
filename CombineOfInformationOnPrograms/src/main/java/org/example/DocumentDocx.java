package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.example.workstation.Computer;
import org.example.workstation.Program;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    public static void writeAllProgramsWithLocalization(List<Computer> list) {
        Map<Program, List<String>> map = getProgramsWithLocalization(list);

        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFTable table = doc.createTable();

            XWPFTableRow row0 = table.getRow(0);
            row0.getCell(0).setText("№");
            row0.addNewTableCell().setText("Наименование программного продукта");
            row0.addNewTableCell().setText("Версия");
            row0.addNewTableCell().setText("Разработчик");
            row0.addNewTableCell().setText("Локализация");

            for (Map.Entry<Program, List<String>> pair : map.entrySet()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText("");
                row.getCell(1).setText(pair.getKey().getName());
                row.getCell(2).setText(pair.getKey().getVersion());
                row.getCell(3).setText(pair.getKey().getManufacture());
                StringBuilder builder = new StringBuilder();
                for (String str : pair.getValue()) {
                    builder.append(str). append("; ");
                }
                row.getCell(4).setText(builder.toString());
            }
            try (FileOutputStream out = new FileOutputStream(
                    "src/main/resources/resultDirectory/Общий список программ с локализацией.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Program, List<String>> getProgramsWithLocalization(List<Computer> list) {
        Map<Program, List<String>> map = new TreeMap<>();
        for (Computer computer : list) {
            for (Program program : computer.getProgramSet()) {
                List<String> stringList;
                if (map.containsKey(program)) {
                    stringList = map.get(program);
                } else {
                    stringList = new ArrayList<>();
                    map.put(program, stringList);
                }
                stringList.add(computer.getName());
            }
        }
        return map;
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