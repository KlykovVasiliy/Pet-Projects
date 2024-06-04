package org.example;


public enum PathsInTheRegistry {
    PROGRAM_FILES("HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\"),
    PROGRAM_FILES_X86("HKLM\\Software\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\"),
    FRAGMENT_REGISTRY_PATH("Microsoft\\Windows\\CurrentVersion\\Uninstall");
//    PROGRAM_DATA("");

    private final String pathRegistry;

    PathsInTheRegistry(String pathRegistry) {
        this.pathRegistry = pathRegistry;
    }

    public String getPathRegistry() {
        return pathRegistry;
    }
}
