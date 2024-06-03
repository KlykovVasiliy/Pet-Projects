package com.example.working_with_files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directory {

    private Directory() {
    }

    public static Set<String> getListFiles(String dir) {
        Pattern pattern1 = Pattern.compile("\\.html$");
        Pattern pattern2 = Pattern.compile("\\.htm$");
        Set<String> setFiles;
        try (Stream<Path> stream = Files.list(Paths.get(dir))) {
            setFiles = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(dir::concat)
                    .filter(name -> {
                        Matcher matcher1 = pattern1.matcher(name);
                        Matcher marcher2 = pattern2.matcher(name);
                        return matcher1.find() || marcher2.find();
                    })
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return setFiles;
    }
}
