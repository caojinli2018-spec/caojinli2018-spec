package dev.jinli.complexity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryAlertStore implements AlertStore {
    private final List<Alert> alerts = new ArrayList<Alert>();

    @Override
    public void save(Alert alert) {
        for (int i = alerts.size() - 1; i >= 0; i--) {
            if (alerts.get(i).id().equals(alert.id())) {
                alerts.remove(i);
            }
        }
        alerts.add(alert);
    }

    @Override
    public Optional<Alert> get(String id) {
        for (Alert alert : alerts) {
            if (alert.id().equals(id)) {
                return Optional.of(alert);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Alert> openAlerts() {
        List<Alert> open = new ArrayList<Alert>();
        for (Alert alert : alerts) {
            if (alert.status() == AlertStatus.OPEN) {
                open.add(alert);
            }
        }
        return open;
    }
}
