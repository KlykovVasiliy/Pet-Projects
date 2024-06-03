package org.example.model;

import org.example.PathsInTheRegistry;
import org.springframework.stereotype.Component;

@Component
public class Registry {
    private PathsInTheRegistry pathsInTheRegistry;

    public PathsInTheRegistry getPathsInTheRegistry() {
        return pathsInTheRegistry;
    }

    public void setPathsInTheRegistry(PathsInTheRegistry pathsInTheRegistry) {
        this.pathsInTheRegistry = pathsInTheRegistry;
    }
}
