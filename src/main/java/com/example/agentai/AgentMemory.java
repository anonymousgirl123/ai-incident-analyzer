package com.example.agentai;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class AgentMemory {

    private final Map<String, Integer> badFeedbackCount = new HashMap<>();

    public void recordBadFeedback(String service) {
        badFeedbackCount.merge(service, 1, Integer::sum);
    }

    public boolean shouldEscalate(String service) {
        return badFeedbackCount.getOrDefault(service, 0) >= 2;
    }
}
