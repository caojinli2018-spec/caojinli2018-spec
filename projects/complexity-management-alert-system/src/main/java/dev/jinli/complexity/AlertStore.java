package dev.jinli.complexity;

import java.util.List;
import java.util.Optional;

public interface AlertStore {
    void save(Alert alert);

    Optional<Alert> get(String id);

    List<Alert> openAlerts();
}
