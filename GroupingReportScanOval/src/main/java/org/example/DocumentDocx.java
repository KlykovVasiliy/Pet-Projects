package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;

import java.util.Map;

public class DocumentDocx {
    private XWPFDocument xwpfDocument;

    private void createDocument() {
        XWPFDocument document = new XWPFDocument();
        CTPageSz pageSz = document.getDocument().addNewBody().addNewSectPr().addNewPgSz();
        pageSz.setOrient(STPageOrientation.LANDSCAPE);
        pageSz.setW(16840);
        pageSz.setH(11900);
        xwpfDocument = document;
    }

    private XWPFTable getTableFromDocument() {
        XWPFTable table = xwpfDocument.createTable();
        XWPFTableRow row = table.getRow(0);
        row.getCell(0).setText("№");
        row.addNewTableCell().setText("Ключи программы");
        row.addNewTableCell().setText("Описание программ");
        row.addNewTableCell().setText("Уязвимости");
        row.addNewTableCell().setText("Количество уязвимостей");
        return table;
    }

    private void insertDataInDocumentWithTable(Map<String, Vulnerabilitie> map) {
        XWPFTable table = getTableFromDocument();
        int positionRow = 1;
        for (Vulnerabilitie vulnerabilitie : map.values()) {
            var key = vulnerabilitie.getKeyProgram();
            var description = vulnerabilitie.getDescProgram();
            var bdu = vulnerabilitie.getBdu();

            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(String.valueOf(positionRow));
            row.getCell(1).setText(key);
            row.getCell(2).setText(description);
            row.getCell(3).setText(bdu);
            row.getCell(4).setText(String.valueOf(bdu.split(", ").length));

            positionRow++;
        }
    }

    public XWPFDocument getDocument(Map<String, Vulnerabilitie> map) {
        createDocument();
        insertDataInDocumentWithTable(map);
        return xwpfDocument;
    }
}