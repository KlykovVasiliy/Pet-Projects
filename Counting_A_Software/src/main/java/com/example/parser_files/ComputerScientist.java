package com.example.parser_files;

import com.example.model.Computer;

import java.util.List;

public interface ComputerScientist {
    public List<Computer> getComputers();

    public void setPathFileAndCharset(String pathFile, String charsetName);
}
