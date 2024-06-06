package org.example.services;

import org.example.model.SystemInfo;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class SystemInfoService {
    private SystemInfo systemInfo;
    private final List<String> listInformationString = new ArrayList<>();

    public void startCollectingInformation() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("systemInfo");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "CP866"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                line = line.trim();
                listInformationString.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        addInformationInSystemInfo();
    }

    private void addInformationInSystemInfo() {
        List<String> listOfCorrections = new ArrayList<>();
        systemInfo = new SystemInfo();
        for (int index = 0; index < listInformationString.size(); index++) {
            String line = listInformationString.get(index);
            setParametersForSystemInfo(line);
            int arraySize = 0;
            if (line.contains("Исправление(я)")) {
                String valueWithPoint = line.split(" - ")[1];
                arraySize = Integer.parseInt(
                        valueWithPoint.substring(0, valueWithPoint.length() - 1));
                listOfCorrections = new ArrayList<>(arraySize);
            }
            int i = 1;
            while (i <= arraySize) {
                line = listInformationString.get(index + i);
                listOfCorrections.add(line.trim());
                i++;
            }
            systemInfo.setListOfCorrections(listOfCorrections);
        }
    }

    private void setParametersForSystemInfo(String line) {
        String[] arrays = line.split(" {2,}");
        if (arrays.length == 2) {
            String parameter = arrays[0];
            String value = arrays[1];
            switch (parameter) {
                case "Имя узла:" -> systemInfo.setNamePC(value);
                case "Название ОС:" -> systemInfo.setOsName(value);
                case "Версия ОС:" -> systemInfo.setOsVersion(value.split(" ")[0]);
                case "Изготовитель ОС:" -> systemInfo.setPublisher(value);
                case "Дата установки:" -> systemInfo.setDateInstallation(value);
                case "Время загрузки системы:" -> systemInfo.setTimeStartOS(value);
                case "Тип системы:" -> systemInfo.setSystemType(value);
                case "Домен:" -> systemInfo.setDomain(value);
                case "Сервер входа в сеть:" -> systemInfo.setServerInputNetwork(value);
            }
        }
    }

    public SystemInfo getSystemInfo() {
        startCollectingInformation();
        return systemInfo;
    }
}
