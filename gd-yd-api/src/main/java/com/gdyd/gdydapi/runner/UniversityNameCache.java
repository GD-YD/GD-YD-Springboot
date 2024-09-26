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
public class UniversityNameCache {
    private final Set<String> universityNames = new HashSet<>();

    @PostConstruct
    public void loadUniversityNames() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/universityName.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                universityNames.add(line.trim());
            }
        }
    }

    public boolean isValidUniversity(String universityName) {
        return !universityNames.contains(universityName);
    }
}
