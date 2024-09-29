package com.gdyd.gdydapi.runner;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class HighSchoolNameCache {
    private final Set<String> highSchoolNames = new HashSet<>();

    @PostConstruct
    public void loadHighSchoolNames() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/highschoolName.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                highSchoolNames.add(line.trim());
            }
        }
    }

    public boolean isValidHighSchool(String highSchoolName) {
        return !highSchoolNames.contains(highSchoolName);
    }
}
