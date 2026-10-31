package learn.designpatterns.behavioral.mediator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import learn.designpatterns.behavioral.mediator.ChatRoomMediator.ChatRoom;
import learn.designpatterns.behavioral.mediator.ChatRoomMediator.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChatRoomMediatorTest {

    private ChatRoom room;
    private User ana;
    private User ben;
    private User cy;

    @BeforeEach
    void setUp() {
        room = new ChatRoom();
        ana = room.join("Ana");
        ben = room.join("Ben");
        cy = room.join("Cy");
    }

    @Test
    @DisplayName("a message reaches every member except the sender")
    void broadcastSkipsSender() {
        ana.send("Hi all");
        assertEquals(List.of("Ana: Hi all"), ben.inbox());
        assertEquals(List.of("Ana: Hi all"), cy.inbox());
        assertEquals(List.of(), ana.inbox());
    }

    @Test
    @DisplayName("a private message reaches only its target")
    void privateMessage() {
        ana.sendTo("Ben", "lunch?");
        assertEquals(List.of("Ana (private): lunch?"), ben.inbox());
        assertEquals(List.of(), cy.inbox());
    }

    @Test
    @DisplayName("the room keeps the full conversation in order")
    void roomLogsConversation() {
        ana.send("Hi all");
        ben.send("Hello");
        assertEquals(List.of("Ana: Hi all", "Ben: Hello"), room.log());
    }

    @Test
    @DisplayName("after leaving, a member neither receives nor can send")
    void leavingStopsDelivery() {
        room.leave(cy);
        ana.send("Still here?");
        assertEquals(List.of(), cy.inbox());
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> cy.send("Wait"));
        assertEquals("Cy is not in the room", ex.getMessage());
    }

    @Test
    @DisplayName("two members cannot share a name")
    void duplicateNameRejected() {
        assertThrows(IllegalArgumentException.class, () -> room.join("Ana"));
    }

    @Test
    @DisplayName("a private message to an unknown member is rejected")
    void unknownTargetRejected() {
        assertThrows(IllegalArgumentException.class, () -> ana.sendTo("Zed", "hello?"));
    }
}
