package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class SystemInfoTest {
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

    @Test
    void convertPojoToJsonString() throws JsonProcessingException {
        SystemInfo systemInfo = getSystemInfo();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(systemInfo);
        System.out.println(json);
    }
}
