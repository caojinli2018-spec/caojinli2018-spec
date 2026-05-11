package dev.jinli.complexity;

public class SystemSignal {
    private final String service;
    private final int cpuPercent;
    private final double errorRate;

    public SystemSignal(String service, int cpuPercent, double errorRate) {
        this.service = service;
        this.cpuPercent = cpuPercent;
        this.errorRate = errorRate;
    }

    public String service() {
        return service;
    }

    public int cpuPercent() {
        return cpuPercent;
    }

    public double errorRate() {
        return errorRate;
    }
}
