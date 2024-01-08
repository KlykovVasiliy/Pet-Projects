package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SourceFilesParser {
    private Map<String, Vulnerabilitie> vulnerabilitieMap;

    public SourceFilesParser() {
        vulnerabilitieMap = new HashMap<>();
    }

    public Elements getElementsTable(String pathFile) {
        File file = new File(pathFile);
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements vulnerabilities = doc.getElementsByClass("vulnerabilities");
        Element table = vulnerabilities.select("table").first();
        return table.select("tr");
    }

    public void readDataFromTableBDU(Elements rowsTable) {
        for (int index = 1; index < rowsTable.size(); index += 2) {
            String bdu = rowsTable.get(index).getElementsByClass("bdu").text();
            String keyProgram = rowsTable.get(index + 1).getElementsByClass("fileslist prods").text();
            String descProgram = rowsTable.get(index + 1).getElementsByClass("desc fileslist").text();
            List<String> keysList = getKeysFromText(keyProgram);
            List<String> descProgramList = getDescriptionsPrograms(descProgram);

            addToMapBDU(bdu, keysList, descProgramList);
        }
    }

    private void addToMapBDU(String bdu, List<String> keysList,
                             List<String> descProgramList) {
        Vulnerabilitie vulnerabilitie;
        for (int indexKey = 0; indexKey < keysList.size(); indexKey++) {
            String key = keysList.get(indexKey);
            String description = "";
            description = descProgramList.get(descProgramList.size() < keysList.size() ? 0 : indexKey);

            if (vulnerabilitieMap.containsKey(key)) {
                vulnerabilitie = vulnerabilitieMap.get(key);
                vulnerabilitie.addToBduList(bdu);
            } else {
                Set<String> set = new TreeSet<>();
                set.add(bdu);
                vulnerabilitie = new Vulnerabilitie(key, description, set);
            }
            vulnerabilitieMap.put(key, vulnerabilitie);
        }
    }

    public Map<String, Vulnerabilitie> getVulnerabilitieMap() {
        return vulnerabilitieMap;
    }

    public List<String> getKeysFromText(String keyPrograms) {
        String key = getKeyWithoutPrefix(keyPrograms);
        String[] strings = key.split(" ");
        Arrays.sort(strings, String::compareTo);
        return Arrays.asList(strings);
    }

    public String getKeyWithoutPrefix(String key) {
        String prefixProgram = "cpe:/a:";
        String prefixOS = "cpe:/o:";
        if (key.length() > prefixProgram.length()) {
            key = key.replace(prefixProgram, "");
            key = key.replace(prefixOS, "");
        }
        return key;
    }

    public List<String> getDescriptionsPrograms(String descProgram) {
        String[] strings = descProgram.split("C:");
        List<String> resultList = new ArrayList<>();
        for (String str : strings) {
            if (str.length() > 0) {
                resultList.add("C:".concat(str).strip());
            }
        }
        resultList.sort(String::compareTo);
        if (descProgram.length() == 0) {
            resultList.add("");
        }
        return resultList;
    }
}