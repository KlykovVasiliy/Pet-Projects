package org.example;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

import java.util.Collection;

public class DocumentExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFCell cell;
    private XSSFRow row;

    private void createDocument() {
        workbook = new XSSFWorkbook();
        createTitleForDocument();
    }

    private void createTitleForDocument() {
        sheet = workbook.createSheet("Лист");
        XSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(0);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Ключи программы");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Описание программ");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Уязвимости");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Количество уязвимостей");
        cell.setCellStyle(style);
    }

    private void insertData(Collection<Vulnerabilitie> collection) {
        int rowNum = 1;
        for (Vulnerabilitie vulnerabilitie : collection) {
            var key = vulnerabilitie.getKeyProgram();
            var description = vulnerabilitie.getDescProgram();
            var bdu = vulnerabilitie.getBduSet();

            row = sheet.createRow(rowNum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(key);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(description);

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(bdu.toString());

            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(bdu.size());
            rowNum++;
        }
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public XSSFWorkbook getDocument(Collection<Vulnerabilitie> collection) {
        createDocument();
        insertData(collection);
        return workbook;
    }
}