package com.example.agentai;

import org.springframework.stereotype.Component;

@Component
public class LLMTool {
    public String call(String prompt) {
        return "{\"summary\":\"Likely deployment-related failure\"}";
    }
}
