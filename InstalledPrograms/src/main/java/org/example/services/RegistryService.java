package org.example.services;

import org.example.PathsInTheRegistry;
import org.example.model.Registry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistryService {

    private Registry registry;
    private List<String> listInstalledPrograms = new ArrayList<>();

    @Autowired
    public RegistryService(Registry registry) {
        this.registry = registry;
    }

    public List<String> getListPathsProgramsAndParametersFromRegistry() {
        Process process = getProcess(registry.getPathsInTheRegistry().getPathRegistry());
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
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
        registry.setPathsInTheRegistry(pathsInTheRegistry);
    }
}
