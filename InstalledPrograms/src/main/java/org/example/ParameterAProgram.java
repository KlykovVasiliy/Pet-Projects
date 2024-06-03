package org.example;

public enum ParameterAProgram {
    DISPLAY_NAME("DisplayName"),
    DISPLAY_VERSION("DisplayVersion"),
    PUBLISHER("Publisher");

    private final String parameter;

    ParameterAProgram(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
