package org.example;

public enum ParameterAProgram {
    DISPLAY_NAME("DisplayName"),
    DISPLAY_VERSION("DisplayVersion"),
    INSTALL_LOCATION("InstallLocation"),
    PUBLISHER("Publisher");

    private final String parameter;

    ParameterAProgram(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
