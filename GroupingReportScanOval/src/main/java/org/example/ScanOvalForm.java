package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;

public class ScanOvalForm extends JFrame {
    private JPanel mainPanel;
    private JLabel labelReadPath;
    private JButton buttonSelectReadFiles;
    private JButton buttonSelectDirSave;
    private JButton buttonStartProcessing;
    private JLabel labelWritePath;
    private JScrollPane jScrollPaneSelectedFiles;
    private JTextArea textAreaSelectedFiles;
    private JTextField textFieldSelectDirSave;
    private JScrollPane jScrollPaneSaveFiles;

    public ScanOvalForm() {
        buttonSelectReadFiles.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jFileChooser.setFileFilter(new FileFilterHTML());
                jFileChooser.setMultiSelectionEnabled(true);
                int returnValue = jFileChooser.showOpenDialog(ScanOvalForm.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] files = jFileChooser.getSelectedFiles();
                    StringJoiner joiner = new StringJoiner(";\n");
                    joiner.add(files[0].getParent().trim());
                    for (File file : files) {
                        joiner.add(file.getName());
                    }
                    textAreaSelectedFiles.setText(String.valueOf(joiner));
                }
            }
        });

        buttonSelectDirSave.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = jFileChooser.showOpenDialog(ScanOvalForm.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File directory = jFileChooser.getSelectedFile();
                    textFieldSelectDirSave.setText(directory.getAbsolutePath());
                }
            }
        });

        buttonStartProcessing.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                SourceFilesParser parser = new SourceFilesParser();
                String[] strings = textAreaSelectedFiles.getText().split(";\n");
                String directory = strings[0];
                for (int i = 1; i < strings.length; i++) {
                    File file = Paths.get(directory, strings[i]).toFile();
                    parser.readDataFromTableBDU(parser.getElementsTable(file));
                    Map<String, Vulnerabilitie> vulnerabilitieMap = new TreeMap<>(parser.getVulnerabilitieMap());
                    DocumentDocx documentDocx = new DocumentDocx();
                    XWPFDocument document = documentDocx.getDocument(vulnerabilitieMap);

                    String fileRecorded = Paths.get(textFieldSelectDirSave.getText(), strings[i]).toString();
                    FileToWrite.write(fileRecorded, document);
                }
                textAreaSelectedFiles.setText("");
                textFieldSelectDirSave.setText("");
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}