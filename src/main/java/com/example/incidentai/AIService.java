package com.example.incidentai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final LLMClient llm;
    private final PromptRegistry prompts;
    private final IncidentStore store;
    private final MeterRegistry metrics;
    private final ObjectMapper mapper = new ObjectMapper();

    public AIService(LLMClient llm, PromptRegistry prompts,
                     IncidentStore store, MeterRegistry metrics) {
        this.llm = llm;
        this.prompts = prompts;
        this.store = store;
        this.metrics = metrics;
    }

    @Async
    public void analyze(Incident i) {
        long start = System.currentTimeMillis();
        try {
            String prompt = prompts.getPrompt("v1", i);
            String raw = llm.callLLM(prompt);
            JsonNode json = mapper.readTree(raw);
            i.summary = json.get("summary").asText();
            i.rootCauses = json.get("rootCauses").toString();
            i.confidence = json.get("confidence").asDouble();
            i.promptVersion = "v1";
            store.save(i);

            metrics.counter("ai_requests_total").increment();
        } catch (Exception e) {
            metrics.counter("ai_failures_total").increment();
        } finally {
            metrics.timer("ai_latency_ms")
                   .record(System.currentTimeMillis() - start,
                           java.util.concurrent.TimeUnit.MILLISECONDS);
        }
    }
}
