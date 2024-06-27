package org.example.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Computer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
@Qualifier(value = "writeToJson")
public class WriteToJson implements WriterDocument {

    @Override
    public void writerToDocument(Computer computer) {
        ObjectMapper objectMapper = new ObjectMapper();
        try(BufferedWriter writer = Files.newBufferedWriter(Paths.get(computer.getSystemInfo()
                .getNamePC().concat(".json")), StandardCharsets.UTF_8
                , StandardOpenOption.CREATE)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, computer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
