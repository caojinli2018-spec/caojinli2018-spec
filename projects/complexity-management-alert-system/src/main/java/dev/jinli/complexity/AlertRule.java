package dev.jinli.complexity;

import java.util.Optional;

public interface AlertRule {
    Optional<RuleMatch> evaluate(SystemSignal signal);
}
