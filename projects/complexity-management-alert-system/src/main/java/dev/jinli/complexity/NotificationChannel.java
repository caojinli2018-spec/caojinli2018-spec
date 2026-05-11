package dev.jinli.complexity;

public interface NotificationChannel {
    String name();

    void send(Alert alert);
}
