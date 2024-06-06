package org.example.services;

import org.example.ParameterAProgram;
import org.example.PathsInTheRegistry;
import org.example.model.Program;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {
    private final RegistryService registryService;

    private final List<Program> programList = new ArrayList<>();

    @Autowired
    public ProgramService(RegistryService registryService) {
        this.registryService = registryService;
        setPathRegistry(PathsInTheRegistry.PROGRAM_FILES);
        setPathRegistry(PathsInTheRegistry.PROGRAM_FILES_X86);
    }

    private void setPathRegistry(PathsInTheRegistry pathsInTheRegistry) {
        registryService.setPathRegistry(pathsInTheRegistry);
        readInformationAboutPrograms();
    }

    public List<Program> getListPrograms() {
        return programList.stream()
                .sorted(Comparator.comparing(Program::getDisplayName)
                        .thenComparing(Program::getDisplayVersion))
                .distinct()
                .collect(Collectors.toList());
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
        } else return nameParameter.equals(ParameterAProgram.INSTALL_LOCATION.getParameter());
    }

    private String getValueOfParameter(String line, String parameter) {
        String value = "";
        if (line.contains(parameter)) {
            String[] parts = line.split(" {4}");
            value = parts.length == 3 ? parts[parts.length - 1] : value;
        }
        return value;
    }

    private void replaceNullOnTheEmptyString(Program program) {
        if (program.getDisplayVersion() == null) {
            program.setDisplayVersion("");
        }
        if (program.getInstallLocation() == null) {
            program.setInstallLocation("");
        }
        if (program.getPublisher() == null) {
            program.setPublisher("");
        }
    }

    private void setParametersProgram(Program program, String line) {
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

    private void readInformationAboutPrograms() {
        List<String> list = registryService.getListPathsProgramsAndParametersFromRegistry();
        Program program = null;
        for (String line : list) {
            if (line.contains(PathsInTheRegistry.FRAGMENT_REGISTRY_PATH.getPathRegistry())) {
                if (program != null &&
                        (program.getDisplayName() != null && !program.getDisplayName().isEmpty())) {
                    replaceNullOnTheEmptyString(program);
                    programList.add(program);
                }
                program = new Program();
                continue;
            }
            setParametersProgram(program, line);
        }
    }

}
