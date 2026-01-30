package com.example.incidentai;

import org.springframework.web.bind.annotation.*;

@RestController
public class FeedbackController {

    private final IncidentStore store;

    public FeedbackController(IncidentStore store) {
        this.store = store;
    }

    @PostMapping("/feedback")
    public void feedback(@RequestBody IncidentFeedback f) {
        store.saveFeedback(f);
    }
}
