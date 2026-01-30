package com.example.incidentai;

import org.springframework.stereotype.Component;

@Component
public class LLMClient {
    public String callLLM(String prompt) {
        return "{\"summary\":\"High error rate detected\",\"rootCauses\":[\"Recent deployment\"]}";
    }
}
