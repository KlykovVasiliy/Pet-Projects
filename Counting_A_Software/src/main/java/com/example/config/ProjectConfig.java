package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.example.repositories", "com.example.services",
        "com.example.parser_files", "com.example.working_with_files"})
public class ProjectConfig {

}
