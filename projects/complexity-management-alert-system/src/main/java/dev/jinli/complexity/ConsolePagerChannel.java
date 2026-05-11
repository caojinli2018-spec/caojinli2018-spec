package dev.jinli.complexity;

public class ConsolePagerChannel implements NotificationChannel {

    @Override
    public String name() {
        return "pager";
    }

    @Override
    public void send(Alert alert) {
        System.out.println("[PAGER] *** URGENT *** " + alert.severity()
                + " | service=" + alert.service()
                + " | reason=" + alert.reason()
                + " | id=" + alert.id());
    }
}
