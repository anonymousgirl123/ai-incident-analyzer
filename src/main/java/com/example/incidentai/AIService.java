package com.example.incidentai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final LLMClient llm;
    private final PromptRegistry prompts;
    private final IncidentStore store;
    private final ObjectMapper mapper = new ObjectMapper();

    public AIService(LLMClient llm, PromptRegistry prompts, IncidentStore store) {
        this.llm = llm;
        this.prompts = prompts;
        this.store = store;
    }

    @Async
    public void analyze(Incident i) {
        try {
            String prompt = prompts.getPrompt("v1", i);
            String raw = llm.callLLM(prompt);
            JsonNode json = mapper.readTree(raw);
            i.summary = json.get("summary").asText();
            i.rootCauses = json.get("rootCauses").toString();
            i.promptVersion = "v1";
            store.save(i);
        } catch (Exception e) {
            i.summary = "AI failed";
        }
    }
}
