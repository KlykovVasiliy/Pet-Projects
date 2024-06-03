package com.example.model;

import java.util.Objects;

public class OperationSystem {

    private String name;
    private String version;

    public OperationSystem(String name) {
        this.name = name;
    }

    public OperationSystem(String name, String version) {
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

    @Override
    public String toString() {
        return "OperationSystem {" +
                "name = '" + name + '\'' +
                ", version = '" + version + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationSystem)) {
            return false;
        }
        OperationSystem os = (OperationSystem) o;
        return os.getName().equals(this.name) && os.getVersion().equals(this.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version);
    }
}
