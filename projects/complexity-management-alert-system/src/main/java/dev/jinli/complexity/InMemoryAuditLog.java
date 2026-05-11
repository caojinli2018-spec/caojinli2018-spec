package dev.jinli.complexity;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuditLog implements AuditLog {
    private final List<String> entries = new ArrayList<String>();

    @Override
    public void record(String entry) {
        entries.add(entry);
    }

    public List<String> entries() {
        return entries;
    }
}
