package com.example.agentai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SREAgent {

    private final AgentMemory memory;
    private final PromptRegistry prompts;
    private final LLMTool llm;
    private final IncidentStore store;
    private final ObjectMapper mapper = new ObjectMapper();

    public SREAgent(AgentMemory memory,
                    PromptRegistry prompts,
                    LLMTool llm,
                    IncidentStore store) {
        this.memory = memory;
        this.prompts = prompts;
        this.llm = llm;
        this.store = store;
    }

    public void handleIncident(Incident incident) {

        AgentAction action = decide(incident);
        incident.agentAction = action.name();

        switch (action) {
            case SKIP_ANALYSIS ->
                incident.summary = "Skipped by agent (low severity)";

            case BASIC_SUMMARY ->
                runLLM(incident, "v1-basic");

            case DEEP_ANALYSIS ->
                runLLM(incident, "v2-deep");

            case REQUEST_HUMAN_REVIEW ->
                incident.summary = "Agent requests human review";
        }

        store.save(incident);
    }

    private AgentAction decide(Incident i) {

        if ("LOW".equals(i.severity)) return AgentAction.SKIP_ANALYSIS;

        if (memory.shouldEscalate(i.service))
            return AgentAction.REQUEST_HUMAN_REVIEW;

        return "CRITICAL".equals(i.severity)
                ? AgentAction.DEEP_ANALYSIS
                : AgentAction.BASIC_SUMMARY;
    }

    private void runLLM(Incident i, String version) {
        try {
            String prompt = prompts.prompt(version, i);
            String raw = llm.call(prompt);
            JsonNode json = mapper.readTree(raw);
            i.summary = json.get("summary").asText();
            i.promptVersion = version;
        } catch (Exception e) {
            i.summary = "Agent failed during AI step";
        }
    }
}
