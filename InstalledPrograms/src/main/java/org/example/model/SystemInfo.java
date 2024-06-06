package org.example.model;

import java.util.List;

public class SystemInfo {
    private String namePC;
    private String osName;
    private String osVersion;
    private String publisher;
    private String dateInstallation;
    private String timeStartOS;

    private String systemType;
    private String domain;
    private String serverInputNetwork;
    private List<String> listOfCorrections;

    public String getNamePC() {
        return namePC;
    }

    public void setNamePC(String namePC) {
        this.namePC = namePC;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDateInstallation() {
        return dateInstallation;
    }

    public void setDateInstallation(String dateInstallation) {
        this.dateInstallation = dateInstallation;
    }

    public String getTimeStartOS() {
        return timeStartOS;
    }

    public void setTimeStartOS(String timeStartOS) {
        this.timeStartOS = timeStartOS;
    }

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getServerInputNetwork() {
        return serverInputNetwork;
    }

    public void setServerInputNetwork(String serverInputNetwork) {
        this.serverInputNetwork = serverInputNetwork;
    }

    public List<String> getListOfCorrections() {
        return listOfCorrections;
    }

    public void setListOfCorrections(List<String> listOfCorrections) {
        this.listOfCorrections = listOfCorrections;
    }

    @Override
    public String toString() {
        return "SystemInfo:" + "\n" +
                "namePC= " + namePC + "\n" +
                "osName= " + osName + "\n" +
                "osVersion= " + osVersion + "\n" +
                "publisher= " + publisher + "\n" +
                "dateInstallation= " + dateInstallation + "\n" +
                "timeStartOS= " + timeStartOS + "\n" +
                "systemType= " + systemType + "\n" +
                "domain= " + domain + "\n" +
                "serverInputNetwork= " + serverInputNetwork + "\n" +
                "listOfCorrections= " + listOfCorrections;
    }
}
