package org.example.model;

import java.util.Objects;

public class Program {
    private String displayName;
    private String displayVersion;
    private String installLocation;
    private String publisher;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayVersion() {
        return displayVersion;
    }

    public void setDisplayVersion(String displayVersion) {
        this.displayVersion = displayVersion;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(displayName, program.displayName) && Objects.equals(displayVersion, program.displayVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, displayVersion);
    }

    @Override
    public String toString() {
        return "DisplayName: " + displayName + "\n" +
                "DisplayVersion: " + displayVersion + "\n" +
                "Publisher: " + publisher + "\n" +
                "InstallLocation: " + installLocation + "\n";
    }
}
