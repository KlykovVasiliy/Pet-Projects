package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputerTest {
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
    void convertPojoToJsonString() throws JsonProcessingException {
        String result = "{\n" +
                "  \"systemInfo\" : {\n" +
                "    \"namePC\" : \"TEST-ARM\",\n" +
                "    \"osName\" : \"Майкрософт Windows 10 Pro\",\n" +
                "    \"osVersion\" : \"10.0.19045\",\n" +
                "    \"publisher\" : \"Microsoft Corporation\",\n" +
                "    \"dateInstallation\" : \"25.07.2023 14:17:32\",\n" +
                "    \"timeStartOS\" : \"13.06.2024 16:59:10\",\n" +
                "    \"systemType\" : \"x64-based PC\",\n" +
                "    \"domain\" : \"WORKGROUP\",\n" +
                "    \"serverInputNetwork\" : \"\\\\DESKTOP-A48JU9J\",\n" +
                "    \"listOfCorrections\" : [ \"[01]: KB1111111\", \"[02]: KB2222222\", \"[03]: KB3333333\" ]\n" +
                "  },\n" +
                "  \"programList\" : [ {\n" +
                "    \"displayName\" : \"7-Zip\",\n" +
                "    \"displayVersion\" : \"23.0.1\",\n" +
                "    \"installLocation\" : \"C:\\\\ProgramFiles\",\n" +
                "    \"publisher\" : \"Igor Pavlov\"\n" +
                "  }, {\n" +
                "    \"displayName\" : \"Р7-Офис. Профессиональный (десктопная версия\",\n" +
                "    \"displayVersion\" : \"2024.1.3.422\",\n" +
                "    \"installLocation\" : \"C:\\\\ProgramFiles\\\\Р7\",\n" +
                "    \"publisher\" : \"АО \\\"Р7\\\"\"\n" +
                "  } ]\n" +
                "}";

        Computer computer = new Computer();
        computer.setSystemInfo(getSystemInfo());
        computer.setProgramList(getProgramList());

        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(computer);
        Assertions.assertEquals(result, json);
    }

    @Test
    void convertJsonToPojoClass() throws JsonProcessingException {
        String json = "{\n" +
                "  \"systemInfo\" : {\n" +
                "    \"namePC\" : \"TEST-ARM\",\n" +
                "    \"osName\" : \"Майкрософт Windows 10 Pro\",\n" +
                "    \"osVersion\" : \"10.0.19045\",\n" +
                "    \"publisher\" : \"Microsoft Corporation\",\n" +
                "    \"dateInstallation\" : \"25.07.2023 14:17:32\",\n" +
                "    \"timeStartOS\" : \"13.06.2024 16:59:10\",\n" +
                "    \"systemType\" : \"x64-based PC\",\n" +
                "    \"domain\" : \"WORKGROUP\",\n" +
                "    \"serverInputNetwork\" : \"\\\\DESKTOP-A48JU9J\",\n" +
                "    \"listOfCorrections\" : [ \"[01]: KB1111111\", \"[02]: KB2222222\", \"[03]: KB3333333\" ]\n" +
                "  },\n" +
                "  \"programList\" : [ {\n" +
                "    \"displayName\" : \"7-Zip\",\n" +
                "    \"displayVersion\" : \"23.0.1\",\n" +
                "    \"installLocation\" : \"C:\\\\ProgramFiles\",\n" +
                "    \"publisher\" : \"Igor Pavlov\"\n" +
                "  }, {\n" +
                "    \"displayName\" : \"Р7-Офис. Профессиональный (десктопная версия\",\n" +
                "    \"displayVersion\" : \"2024.1.3.422\",\n" +
                "    \"installLocation\" : \"C:\\\\ProgramFiles\\\\Р7\",\n" +
                "    \"publisher\" : \"АО \\\"Р7\\\"\"\n" +
                "  } ]\n" +
                "}";

        Computer computer = objectMapper.readValue(json, Computer.class);
        System.out.println(computer.getSystemInfo());
        System.out.println(computer.getProgramList());
    }
}
