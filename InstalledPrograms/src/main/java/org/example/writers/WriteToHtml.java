package org.example.writers;

import org.example.HtmlTags;
import org.example.model.Computer;
import org.example.model.Program;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringJoiner;

@Component
@Qualifier(value = "writeToHtml")
public class WriteToHtml implements WriterDocument {
    @Override
    public void writerToDocument(Computer computer) {
        String fileName = computer.getSystemInfo().getNamePC().concat(".html");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName)
                , StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
            writer.write(HtmlTags.HTML_START.getTag());
            writer.write(HtmlTags.HEAD.getTag());
            writer.write(HtmlTags.BODY_START.getTag());
            writer.write("\t<tr>".concat(getDateAndTime()).concat("</tr>\n"));
            writer.write(HtmlTags.HEADING_ABOUT_SYSTEM.getTag());
            writer.write(HtmlTags.TABLE_START.getTag());
            writer.write(HtmlTags.HEADINGS_TABLE_ABOUT_SYSTEM.getTag());
            addInformationAboutSystem(writer, computer);
            writer.write(HtmlTags.TABLE_END.getTag());

            writer.write(HtmlTags.HEADING_ABOUT_PROGRAMS.getTag());
            writer.write(HtmlTags.TABLE_START.getTag());
            writer.write(HtmlTags.HEADINGS_TABLE_ABOUT_PROGRAMS.getTag());
            addInformationAboutPrograms(writer, computer);
            writer.write(HtmlTags.TABLE_END.getTag());

            writer.write(HtmlTags.BODY_END.getTag());
            writer.write(HtmlTags.HTML_END.getTag());
            writer.write(HtmlTags.STYLE.getTag());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addInformationAboutSystem(BufferedWriter writer, Computer computer) throws IOException {
        String indent = "\t\t\t<td>";

        StringWriter string = new StringWriter();
        string.write("\t\t<tr>\n");
        string.write(indent.concat(computer.getSystemInfo().getNamePC()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getOsName()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getOsVersion()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getPublisher()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getDateInstallation())
                .concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getTimeStartOS())
                .concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getSystemType()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getDomain()).concat("</td>\n"));
        string.write(indent.concat(computer.getSystemInfo().getServerInputNetwork())
                .concat("</td>\n"));

        StringJoiner joiner = new StringJoiner(", ");
        computer.getSystemInfo().getListOfCorrections().forEach(joiner::add);
        string.write(indent.concat(joiner.toString()).concat("</td>\n"));
        string.write("\t\t</tr>\n");
        writer.write(string.toString());
    }

    private void addInformationAboutPrograms(BufferedWriter writer, Computer computer) throws IOException {
        String indent = "\t\t\t";
        List<Program> programList = computer.getProgramList();
        for (int i = 0; i < programList.size(); i++) {
            StringWriter string = new StringWriter();
            Program program = programList.get(i);
            String number = "<td>".concat(String.valueOf(i + 1)).concat("</td>");
            String name = "<td>".concat(program.getDisplayName()).concat("</td>");
            String version = "<td>".concat(program.getDisplayVersion()).concat("</td>");
            String publisher = "<td>".concat(program.getPublisher()).concat("</td>");
            String location = "<td>".concat(computer.getSystemInfo().getNamePC())
                    .concat("</td>");
            String installLocation = "<td>".concat(program.getInstallLocation())
                    .concat("</td>");

            string.write("\t\t<tr>\n".concat(indent).concat(number).concat(name).concat(version)
                    .concat(publisher).concat(location).concat(installLocation)
                    .concat("\n")
                    .concat("\t\t</tr>"));
            if (i < programList.size() - 1) {
                string.write("\n");
            }
            writer.write(string.toString());
        }
    }

    private String getDateAndTime() {
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern("dd MMMM yyyy, HH:mm:ss");
        return currentDateAndTime.format(dateTimeFormatter);
    }
}
