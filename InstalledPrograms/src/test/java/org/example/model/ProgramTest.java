package org.example.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class ProgramTest {
    ObjectMapper objectMapper = new ObjectMapper();

    private Program getProgram() {
        Program program = new Program();
        program.setDisplayName("7-Zip");
        program.setDisplayVersion("23.0.1");
        program.setInstallLocation("C:\\ProgramFiles");
        program.setPublisher("Igor Pavlov");
        return program;
    }

    @Test
    void convertPojoToJsonString() throws JsonProcessingException {
        Program program = getProgram();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(program);
        System.out.println(json);
    }

}
