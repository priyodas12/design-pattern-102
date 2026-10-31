package learn.designpatterns.behavioral.mediator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Mediator pattern.
 *
 * <p>Analogy: a group chat. Members never message each other directly or keep a list of each other;
 * they post to the chat room, and the room decides who receives what. Adding or removing a member
 * touches only the room, not every other member.
 */
public final class ChatRoomMediator {

    private ChatRoomMediator() {
    }

    /** Mediator: owns the membership and routes every message. */
    static final class ChatRoom {
        private final Map<String, User> members = new LinkedHashMap<>();
        private final List<String> log = new ArrayList<>();

        User join(String name) {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("A member needs a name");
            }
            if (members.containsKey(name)) {
                throw new IllegalArgumentException("Name already taken: " + name);
            }
            User user = new User(name, this);
            members.put(name, user);
            return user;
        }

        void leave(User user) {
            members.remove(user.name(), user);
        }

        /** Sends to everyone except the sender, and records it in the room log. */
        void broadcast(User from, String text) {
            requireMember(from);
            String message = from.name() + ": " + text;
            log.add(message);
            for (User member : members.values()) {
                if (member != from) {
                    member.receive(message);
                }
            }
        }

        /** Sends to one named member only; private messages are not logged. */
        void sendPrivate(User from, String toName, String text) {
            requireMember(from);
            User target = members.get(toName);
            if (target == null) {
                throw new IllegalArgumentException("No member named " + toName);
            }
            target.receive(from.name() + " (private): " + text);
        }

        List<String> log() {
            return Collections.unmodifiableList(log);
        }

        private void requireMember(User user) {
            if (members.get(user.name()) != user) {
                throw new IllegalStateException(user.name() + " is not in the room");
            }
        }
    }

    /** Colleague: knows only the room, never the other users. */
    static final class User {
        private final String name;
        private final ChatRoom room;
        private final List<String> inbox = new ArrayList<>();

        private User(String name, ChatRoom room) {
            this.name = name;
            this.room = room;
        }

        String name() {
            return name;
        }

        void send(String text) {
            room.broadcast(this, text);
        }

        void sendTo(String memberName, String text) {
            room.sendPrivate(this, memberName, text);
        }

        void receive(String message) {
            inbox.add(message);
        }

        List<String> inbox() {
            return Collections.unmodifiableList(inbox);
        }
    }

    public static void main(String[] args) {
        ChatRoom room = new ChatRoom();
        User ana = room.join("Ana");
        User ben = room.join("Ben");
        User cy = room.join("Cy");

        ana.send("Hi all");
        ben.sendTo("Ana", "lunch at 1?");
        cy.send("Count me in");

        System.out.println("Room log:  " + room.log());
        System.out.println("Ana inbox: " + ana.inbox());
        System.out.println("Ben inbox: " + ben.inbox());
        System.out.println("Cy inbox:  " + cy.inbox());
    }
}
