package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortWordsForm {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton sortedButton;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JPanel upperPanel;
    private JTextField textFieldSeparator;
    private JLabel labelSeparatorPointer;
    private JLabel labelCountWords;
    private JLabel labelResultCountWords;

    public SortWordsForm() {
        sortedButton.addActionListener(new Action() {
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
                String separator = textFieldSeparator.getText();
                if (separator.length() > 0) {
                    String[] strings = textArea.getText().split(separator);
                    String result = Stream.of(strings)
                            .map(String::trim)
                            .sorted()
                            .distinct()
                            .collect(Collectors.joining(", "));
                    textArea.setText(result);
                    labelResultCountWords.setText(String.valueOf(result.split(separator).length));
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}