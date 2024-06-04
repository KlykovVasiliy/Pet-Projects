package org.example.model;

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
    public String toString() {
        return "DisplayName: " + displayName + "\n" +
                "DisplayVersion: " + displayVersion + "\n" +
                "Publisher: " + publisher + "\n" +
                "InstallLocation: " + installLocation + "\n";
    }
}
