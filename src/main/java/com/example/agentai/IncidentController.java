package com.example.agentai;

import org.springframework.web.bind.annotation.*;

@RestController
public class IncidentController {

    private final IncidentStore store;
    private final SREAgent agent;

    public IncidentController(IncidentStore store, SREAgent agent) {
        this.store = store;
        this.agent = agent;
    }

    @PostMapping("/incident")
    public Incident create(@RequestParam String service,
                           @RequestParam String severity) {
        Incident i = new Incident();
        i.service = service;
        i.severity = severity;
        store.save(i);
        agent.handleIncident(i);
        return i;
    }

    @GetMapping("/incident/{id}")
    public Incident get(@PathVariable Long id) {
        return store.get(id);
    }

    @PostMapping("/feedback/bad")
    public void badFeedback(@RequestParam String service,
                            AgentMemory memory) {
        memory.recordBadFeedback(service);
    }
}
