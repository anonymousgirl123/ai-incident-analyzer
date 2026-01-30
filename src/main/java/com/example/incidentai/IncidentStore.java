package com.example.incidentai;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IncidentStore {
    private final Map<Long, Incident> incidents = new HashMap<>();
    private final List<IncidentFeedback> feedback = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Incident save(Incident i) {
        if (i.id == null) i.id = seq.getAndIncrement();
        incidents.put(i.id, i);
        return i;
    }

    public Incident get(Long id) {
        return incidents.get(id);
    }

    public void saveFeedback(IncidentFeedback f) {
        feedback.add(f);
    }

    public List<IncidentFeedback> feedback() {
        return feedback;
    }
}
