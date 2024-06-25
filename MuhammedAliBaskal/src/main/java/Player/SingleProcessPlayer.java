package Player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * SingleProcessPlayer class represents a player that can send and receive messages
 * within a single JVM process using blocking queues.
 */
public class SingleProcessPlayer extends AbstractPlayer {
    private BlockingQueue<String> inbox;
    private BlockingQueue<String> outbox;

    
    /**
     * Constructor to initialize the player with inbox and outbox.
     * @param name The name of the player.
     * @param isInitiator Flag indicating if the player is the initiator.
     */
    public SingleProcessPlayer(String name, boolean isInitiator) {
        super(name, isInitiator);
        this.inbox = new LinkedBlockingQueue<>();
    }

    /**
     * Gets the inbox queue.
     * @return The inbox queue.
     */
    public BlockingQueue<String> getInbox() {
        return inbox;
    }

    /**
     * Sets the outbox queue.
     * @param outbox The outbox queue.
     */
    public void setOutbox(BlockingQueue<String> outbox) {
        this.outbox = outbox;
    }

    @Override
    public void sendMessage(String message) {
        try {
            System.out.println(name + " sent: " + message);
            outbox.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    @Override
    public String receiveMessage() {
        try {
            String message = inbox.take();
            System.out.println(name + " received: " + message);
            return message;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to receive message: " + e.getMessage());
            return null;
        }
    }
}
