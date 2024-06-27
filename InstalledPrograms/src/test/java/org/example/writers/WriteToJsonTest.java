package org.example.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Computer;
import org.example.model.Program;
import org.example.model.SystemInfo;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WriteToJsonTest {
    ObjectMapper objectMapper = new ObjectMapper();

    private SystemInfo getSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setNamePC("TEST-ARM");
        systemInfo.setOsName("Майкрософт Windows 10 Pro");
        systemInfo.setOsVersion("10.0.19045");
        systemInfo.setPublisher("Microsoft Corporation");
        systemInfo.setDateInstallation("25.07.2023 14:17:32");
        systemInfo.setTimeStartOS("13.06.2024 16:59:10");
        systemInfo.setSystemType("x64-based PC");
        systemInfo.setDomain("WORKGROUP");
        systemInfo.setServerInputNetwork("\\DESKTOP-A48JU9J");
        systemInfo.setListOfCorrections(Arrays.asList("[01]: KB1111111", "[02]: KB2222222",
                "[03]: KB3333333"));
        return systemInfo;
    }

    private List<Program> getProgramList() {
        List<Program> programList = new ArrayList<>();

        Program program = new Program();
        program.setDisplayName("7-Zip");
        program.setDisplayVersion("23.0.1");
        program.setInstallLocation("C:\\ProgramFiles");
        program.setPublisher("Igor Pavlov");
        programList.add(program);

        program = new Program();
        program.setDisplayName("Р7-Офис. Профессиональный (десктопная версия");
        program.setDisplayVersion("2024.1.3.422");
        program.setInstallLocation("C:\\ProgramFiles\\Р7");
        program.setPublisher("АО \"Р7\"");
        programList.add(program);
        return programList;
    }

    @Test
    void writeToJsonTest() {
        Computer computer = new Computer();
        computer.setSystemInfo(getSystemInfo());
        computer.setProgramList(getProgramList());

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(computer.getSystemInfo()
                        .getNamePC().concat(".json")), StandardCharsets.UTF_8
                , StandardOpenOption.CREATE)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, computer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
