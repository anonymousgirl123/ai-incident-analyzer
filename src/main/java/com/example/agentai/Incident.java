package com.example.agentai;

import java.time.Instant;

public class Incident {
    public Long id;
    public String service;
    public String severity;
    public String summary;
    public String promptVersion;
    public String agentAction;
    public Instant createdAt = Instant.now();
}
