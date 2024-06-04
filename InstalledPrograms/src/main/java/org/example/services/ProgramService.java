package org.example.services;

import org.example.ParameterAProgram;
import org.example.PathsInTheRegistry;
import org.example.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramService {
    private RegistryService registryService;

    private List<Program> programList = new ArrayList<>();

    @Autowired
    public ProgramService(RegistryService registryService) {
        this.registryService = registryService;
    }

    public void setPathRegistry(PathsInTheRegistry pathsInTheRegistry) {
        registryService.setPathRegistry(pathsInTheRegistry);
    }

    public List<Program> getListPrograms() {
        List<String> list = registryService.getListPathsProgramsAndParametersFromRegistry();
        Program program = null;
        for (String line : list) {
            if (line.contains(PathsInTheRegistry.FRAGMENT_REGISTRY_PATH.getPathRegistry())) {
                if (program != null && program.getDisplayName() != null) {
                    programList.add(program);
                }
                program = new Program();
                continue;
            }

            String parameter = getNameParameter(line);
            if (isParameterForProgram(parameter)) {
                String value = getValueOfParameter(line, parameter);
                switch (parameter) {
                    case "DisplayName" -> program.setDisplayName(value);
                    case "DisplayVersion" -> program.setDisplayVersion(value);
                    case "Publisher" -> program.setPublisher(value);
                    case "InstallLocation" -> program.setInstallLocation(value);
                }
            }
        }
        return programList;
    }

    private String getNameParameter(String line) {
        return line.split(" {4}")[0];
    }

    private boolean isParameterForProgram(String nameParameter) {
        if (nameParameter.equals(ParameterAProgram.DISPLAY_NAME.getParameter())) {
            return true;
        } else if (nameParameter.equals(ParameterAProgram.DISPLAY_VERSION.getParameter())) {
            return true;
        } else if (nameParameter.equals(ParameterAProgram.PUBLISHER.getParameter())) {
            return true;
        } else if (nameParameter.equals(ParameterAProgram.INSTALL_LOCATION.getParameter())) {
            return true;
        }else {
            return false;
        }
    }

    private String getValueOfParameter(String line, String parameter) {
        String value = "";

        if (line.contains(parameter)) {
            String[] parts = line.split(" {4}");
            value = parts.length == 3 ? parts[parts.length - 1] : value;
        }
        return value;
    }

}
