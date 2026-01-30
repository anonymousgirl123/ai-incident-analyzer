package com.example.incidentai;

import java.time.Instant;

public class IncidentFeedback {
    public Long incidentId;
    public String rating;
    public String comment;
    public String promptVersion;
    public Instant createdAt = Instant.now();
}
