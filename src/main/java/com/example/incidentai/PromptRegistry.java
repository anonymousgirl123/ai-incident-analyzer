package com.example.incidentai;

import org.springframework.stereotype.Component;

@Component
public class PromptRegistry {
    public String getPrompt(String version, Incident i) {
        return "You are an SRE. Service=" + i.service +
               ", Severity=" + i.severity +
               ". Summarize incident and suggest root cause in JSON.";
    }
}
