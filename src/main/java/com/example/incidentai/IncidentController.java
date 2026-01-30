package com.example.incidentai;

import org.springframework.web.bind.annotation.*;

@RestController
public class IncidentController {

    private final IncidentStore store;
    private final AIService ai;

    public IncidentController(IncidentStore store, AIService ai) {
        this.store = store;
        this.ai = ai;
    }

    @PostMapping("/incident")
    public Incident create(@RequestParam String service,
                           @RequestParam String severity) {
        Incident i = new Incident();
        i.service = service;
        i.severity = severity;
        store.save(i);
        ai.analyze(i);
        return i;
    }

    @GetMapping("/incident/{id}")
    public Incident get(@PathVariable Long id) {
        return store.get(id);
    }
}
