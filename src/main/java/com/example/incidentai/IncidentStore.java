package com.example.incidentai;

import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IncidentStore {
    private final Map<Long, Incident> db = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Incident save(Incident i) {
        if (i.id == null) i.id = seq.getAndIncrement();
        db.put(i.id, i);
        return i;
    }

    public Incident get(Long id) {
        return db.get(id);
    }
}
