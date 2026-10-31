package learn.designpatterns.behavioral.chainofresponsibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain.Agent;
import learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain.Director;
import learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain.Manager;
import learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain.SupportHandler;
import learn.designpatterns.behavioral.chainofresponsibility.SupportEscalationChain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SupportEscalationChainTest {

    private final SupportHandler chain = SupportHandler.chain(new Agent(), new Manager(), new Director());

    @Test
    @DisplayName("a simple ticket is handled by the first level")
    void agentHandlesSimpleTicket() {
        assertEquals("Agent resolved ticket #1 (printer jam)", chain.handle(new Ticket(1, 1, "printer jam")));
    }

    @Test
    @DisplayName("a harder ticket is passed along to the first level that can handle it")
    void escalatesToManager() {
        assertEquals("Manager resolved ticket #2 (refund request)", chain.handle(new Ticket(2, 2, "refund request")));
    }

    @Test
    @DisplayName("the hardest ticket reaches the end of the chain")
    void escalatesToDirector() {
        assertEquals("Director resolved ticket #3 (contract dispute)",
                chain.handle(new Ticket(3, 3, "contract dispute")));
    }

    @Test
    @DisplayName("the chain can be rearranged without changing the sender")
    void chainIsConfigurable() {
        SupportHandler noAgent = SupportHandler.chain(new Manager(), new Director());
        assertEquals("Manager resolved ticket #4 (printer jam)", noAgent.handle(new Ticket(4, 1, "printer jam")));
    }

    @Test
    @DisplayName("a ticket nobody can handle fails with a clear message")
    void unhandledTicketRejected() {
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> chain.handle(new Ticket(5, 4, "data center fire")));
        assertEquals("No one could handle ticket #5 (severity 4)", ex.getMessage());
    }

    @Test
    @DisplayName("the same handler cannot appear twice in a chain")
    void duplicateHandlerRejected() {
        Agent agent = new Agent();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> SupportHandler.chain(agent, new Manager(), agent));
        assertEquals("Handler Agent appears more than once in the chain", ex.getMessage());
    }

    @Test
    @DisplayName("reusing handlers in a new chain does not leave a loop from the old chain")
    void rechainingDoesNotLoop() {
        Agent agent = new Agent();
        Manager manager = new Manager();
        SupportHandler.chain(agent, manager);
        SupportHandler reversed = SupportHandler.chain(manager, agent);
        assertThrows(IllegalStateException.class, () -> reversed.handle(new Ticket(7, 4, "data center fire")));
    }

    @Test
    @DisplayName("severity must be at least 1")
    void invalidSeverityRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Ticket(6, 0, "typo"));
    }
}
