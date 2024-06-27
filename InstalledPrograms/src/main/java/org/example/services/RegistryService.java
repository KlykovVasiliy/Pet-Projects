package org.example.services;

import org.example.PathsInTheRegistry;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistryService {
    private String branchRegistry;
    private List<String> listInstalledPrograms = new ArrayList<>();

    public List<String> getListPathsProgramsAndParametersFromRegistry() {
        Process process = getProcess(branchRegistry);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "CP866"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                listInstalledPrograms.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listInstalledPrograms;
    }

    private Process getProcess(String sectionName) {
        String regQuery = "reg query ";
        String parameters = " /s /t REG_SZ";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(regQuery + sectionName.concat(parameters));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return process;
    }

    public void setPathRegistry(PathsInTheRegistry pathsInTheRegistry) {
        branchRegistry = pathsInTheRegistry.getPathRegistry();
    }
}
