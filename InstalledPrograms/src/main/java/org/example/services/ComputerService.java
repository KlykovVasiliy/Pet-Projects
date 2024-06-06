package org.example.services;

import org.example.model.Program;
import org.example.model.SystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerService {

    @Autowired
    private ProgramService programService;
    @Autowired
    private SystemInfoService systemInfoService;

    public ComputerService(ProgramService programService, SystemInfoService systemInfoService) {
        this.programService = programService;
        this.systemInfoService = systemInfoService;
    }

    public List<Program> getProgramList() {
        return programService.getListPrograms();
    }

    public SystemInfo getInformationAboutSystem() {
        return systemInfoService.getSystemInfo();
    }
}
