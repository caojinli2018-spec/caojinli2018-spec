package dev.jinli.complexity;

import java.util.Optional;

public class CpuPressureRule implements AlertRule {
    private final int threshold;

    public CpuPressureRule(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public Optional<RuleMatch> evaluate(SystemSignal signal) {
        if (signal.cpuPercent() >= threshold) {
            return Optional.of(new RuleMatch(Severity.CRITICAL, "cpu_above_threshold"));
        }
        return Optional.empty();
    }
}
