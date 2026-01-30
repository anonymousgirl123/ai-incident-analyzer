package com.example.incidentai;

import java.time.Instant;

public class Incident {
    public Long id;
    public String service;
    public String severity;
    public String summary;
    public String rootCauses;
    public String promptVersion;
    public double confidence;
    public Instant createdAt = Instant.now();
}
