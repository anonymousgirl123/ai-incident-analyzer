package com.example.agentai;

import org.springframework.stereotype.Component;

@Component
public class PromptRegistry {

    public String prompt(String version, Incident i) {
        return "You are an SRE agent. Service=" + i.service +
               ", Severity=" + i.severity +
               ". Provide summary in JSON.";
    }
}
