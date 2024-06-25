package Player;

/**
 * Abstract base class for Player. Defines common properties and methods.
 * AbstractPlayer class provides a base implementation for a player
 * that can send and receive messages. Subclasses must implement
 * specific send and receive methods for their communication mode.
 */
public abstract class AbstractPlayer implements Runnable {
    protected String name;
    protected boolean isInitiator;
    protected int messageCount;

    /**
     * Constructor to initialize the player.
     * @param name The name of the player.
     * @param isInitiator Flag indicating if the player is the initiator.
     */
    public AbstractPlayer(String name, boolean isInitiator) {
        this.name = name;
        this.isInitiator = isInitiator;
        this.messageCount = 0;
    }
    /**
     * Sends a message.
     * @param message The message to send.
     */
    public abstract void sendMessage(String message);
    /**
     * Receives a message.
     * @return The received message.
     */
    public abstract String receiveMessage();

    @Override
    public void run() {
        if (isInitiator) {
            sendMessage("Hello from " + name);
        }

        while (messageCount < 10) {
            String receivedMessage = receiveMessage();
            if (receivedMessage != null) {
                messageCount++;
                sendMessage(receivedMessage + " " + messageCount);
            }
        }

        System.out.println(name + " finished communication.");
    }
}
