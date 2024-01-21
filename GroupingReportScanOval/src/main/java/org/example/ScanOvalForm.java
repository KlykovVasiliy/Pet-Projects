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
                    StringJoiner joiner = new StringJoiner("\n");
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
                if (textAreaSelectedFiles.getText().length() == 0 ||
                        textFieldSelectDirSave.getText().length() == 0) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Все поля должны быть заполнены",
                            "Внимание!", JOptionPane.WARNING_MESSAGE);
                }
                if (!checkingTheFilesForReading()) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Один или несколько выбранных файлов не существуют",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                if (!checkingTheDirectoryForSaving()) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Указанный каталог для сохранения результата не существует",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                } else {
                    startProcessing();
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void startProcessing() {
        SourceFilesParser parser = new SourceFilesParser();
        for (File file : getFilesForReading()) {
            try {
                parser.readDataFromTableBDU(parser.getElementsTable(file));
            } catch (NullPointerException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(),
                        "Выбран иной файл, выберите файл который является результатом работы ScanOval",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
            Map<String, Vulnerabilitie> vulnerabilitieMap
                    = new TreeMap<>(parser.getVulnerabilitieMap());
            DocumentDocx documentDocx = new DocumentDocx();
            XWPFDocument document = documentDocx.getDocument(vulnerabilitieMap);

            String fileRecorded
                    = Paths.get(textFieldSelectDirSave.getText(), file.getName()).toString();
            FileToWrite.write(fileRecorded, document);
        }
        clearTheInputFields();
    }

    private File[] getFilesForReading() {
        String[] strings = textAreaSelectedFiles.getText().split("\n");
        String directory = strings[0];
        File[] files = new File[strings.length - 1];
        for (int i = 1; i < strings.length; i++) {
            files[i - 1] = Paths.get(directory, strings[i]).toFile();
        }
        return files;
    }

    private boolean checkingTheFilesForReading() {
        boolean returnResult = false;
        for (File file : getFilesForReading()) {
            if (!file.exists()) {
                return false;
            } else {
                returnResult = true;
            }
        }
        return returnResult;
    }

    private boolean checkingTheDirectoryForSaving() {
        File directory = new File(textFieldSelectDirSave.getText());
        return directory.exists();
    }

    private void clearTheInputFields() {
        if (textAreaSelectedFiles.getText().length() > 0) {
            textAreaSelectedFiles.setText("");
        }
        if (textFieldSelectDirSave.getText().length() > 0) {
            textFieldSelectDirSave.setText("");
        }
    }
}