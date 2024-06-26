package com.example.model;

public class Program {
    private String name;
    private String version;
    private String manufacture;

    public Program(String name, String version, String manufacture) {
        this.name = name;
        this.version = version;
        this.manufacture = manufacture;
    }

    public Program(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    @Override
    public String toString() {
        return name + " - " + version + " - " + manufacture;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(name.hashCode());
        result = 31 * result + Integer.hashCode(version.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Program)) {
            return false;
        }
        Program p = (Program) obj;
        return p.name.equals(this.name) && p.version.equals(this.version);
    }
}