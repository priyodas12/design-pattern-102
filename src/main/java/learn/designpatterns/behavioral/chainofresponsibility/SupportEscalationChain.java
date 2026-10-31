package learn.designpatterns.behavioral.chainofresponsibility;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/**
 * Chain of Responsibility pattern.
 *
 * <p>Analogy: a customer support desk. A ticket goes to a support agent first. If it's too hard, the
 * agent passes it to a manager, and the manager to a director. The customer only ever hands the ticket
 * to the front desk and never needs to know who finally solves it.
 */
public final class SupportEscalationChain {

    private SupportEscalationChain() {
    }

    /** The request travelling along the chain. Severity 1 is easiest. */
    record Ticket(int id, int severity, String issue) {
        Ticket {
            if (severity < 1) {
                throw new IllegalArgumentException("Severity must be at least 1, got " + severity);
            }
        }
    }

    /** Handler: either handles the ticket or passes it to the next handler. */
    abstract static class SupportHandler {
        private SupportHandler next;

        /**
         * Links handlers in the given order and returns the first one (the only one a sender needs).
         * Rejects a handler listed twice, and clears the last handler's old link, so a chain can never loop.
         */
        static SupportHandler chain(SupportHandler... handlers) {
            if (handlers.length == 0) {
                throw new IllegalArgumentException("A chain needs at least one handler");
            }
            Set<SupportHandler> seen = Collections.newSetFromMap(new IdentityHashMap<>());
            for (SupportHandler handler : handlers) {
                if (!seen.add(handler)) {
                    throw new IllegalArgumentException("Handler " + handler.title() + " appears more than once in the chain");
                }
            }
            for (int i = 0; i < handlers.length - 1; i++) {
                handlers[i].next = handlers[i + 1];
            }
            handlers[handlers.length - 1].next = null;
            return handlers[0];
        }

        abstract String title();

        abstract boolean canHandle(Ticket ticket);

        /** Handle here if possible, otherwise pass it on; fail clearly at the end of the chain. */
        final String handle(Ticket ticket) {
            if (canHandle(ticket)) {
                return title() + " resolved ticket #" + ticket.id() + " (" + ticket.issue() + ")";
            }
            if (next != null) {
                return next.handle(ticket);
            }
            throw new IllegalStateException(
                    "No one could handle ticket #" + ticket.id() + " (severity " + ticket.severity() + ")");
        }
    }

    /** Concrete handlers: each knows only its own limit. */
    static final class Agent extends SupportHandler {
        String title() {
            return "Agent";
        }

        boolean canHandle(Ticket ticket) {
            return ticket.severity() <= 1;
        }
    }

    static final class Manager extends SupportHandler {
        String title() {
            return "Manager";
        }

        boolean canHandle(Ticket ticket) {
            return ticket.severity() <= 2;
        }
    }

    static final class Director extends SupportHandler {
        String title() {
            return "Director";
        }

        boolean canHandle(Ticket ticket) {
            return ticket.severity() <= 3;
        }
    }

    public static void main(String[] args) {
        SupportHandler frontDesk = SupportHandler.chain(new Agent(), new Manager(), new Director());
        List<Ticket> tickets = List.of(
                new Ticket(1, 1, "printer jam"), new Ticket(2, 2, "refund request"),
                new Ticket(3, 3, "contract dispute"), new Ticket(4, 4, "data center fire"));
        for (Ticket ticket : tickets) {
            try {
                System.out.println(frontDesk.handle(ticket));
            } catch (IllegalStateException e) {
                System.out.println("Escalation failed: " + e.getMessage());
            }
        }
    }
}
