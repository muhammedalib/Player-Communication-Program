package Player;

import java.io.*;

/**
 * MultiProcessPlayer class represents a player that can send and receive messages
 * between different JVM processes using files.
 */
public class MultiProcessPlayer extends AbstractPlayer {
    private File inboxFile;
    private File outboxFile;
    private static final int MAX_MESSAGE = 10;

    /**
     * Constructor to initialize the player with inbox and outbox files.
     * @param name The name of the player.
     * @param isInitiator Flag indicating if the player is the initiator.
     * @param inboxFile The path to the inbox file.
     * @param outboxFile The path to the outbox file.
     */
    public MultiProcessPlayer(String name, boolean isInitiator, String inboxFile, String outboxFile) {
        super(name, isInitiator);
        this.inboxFile = new File(inboxFile);
        this.outboxFile = new File(outboxFile);

        try {
            if (!this.inboxFile.exists()) {
                this.inboxFile.createNewFile();
            }
            if (!this.outboxFile.exists()) {
                this.outboxFile.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Failed to create inbox or outbox file: " + e.getMessage());
        }
    }

    /**
     * Sends a message by writing it to the outbox file.
     * This method is synchronized to ensure thread safety.
     * 
     * @param message The message to send.
     */
    @Override
    public synchronized void sendMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outboxFile, true))) {
            System.out.println(name + " sent: " + message);
            writer.write(message);
            writer.newLine();    // no need to close the writer because when its in the try-catch bloke its automatically done.
        } catch (IOException e) {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    /**
     * Receives a message by reading it from the inbox file.
     * This method is synchronized to ensure thread safety.
     * 
     * @return The received message, or null if no new message is available.
     */
    @Override
    public synchronized String receiveMessage() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(inboxFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to receive message: " + e.getMessage());
        }

        String messages = content.toString();
        String[] splitMessages = messages.split("\n");
        if (splitMessages.length > messageCount) {
            String message = splitMessages[messageCount];
            System.out.println(name + " received: " + message);
            return message;
        }
        return null;
    }
    /**
     * The main run method for the player thread.
     * If the player is the initiator, it sends the initial message.
     * The player then continuously receives and sends messages, updating the message count,
     * until the message count reaches 10. There are small delays to ensure message order.
     */

    @Override
    public void run() {
        if (isInitiator) {
            sendMessage("Hello from " + name);
        }

        while (messageCount < MAX_MESSAGE) {
            String receivedMessage = receiveMessage();
            if (receivedMessage != null) {
                messageCount++;
                sendMessage(receivedMessage + " " + messageCount);
                try {
                    Thread.sleep(200); // Small delay to ensure message order
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Interrupted: " + e.getMessage());
                }
            } else {
                try {
                    Thread.sleep(100); // Small delay to wait for the next message
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Interrupted: " + e.getMessage());
                }
            }
        }

        System.out.println(name + " finished communication.");
    }

    /**
     * Main method to start the MultiProcessPlayer.
     * 
     * @param args Command line arguments: <name> <isInitiator> <inboxFile> <outboxFile>
     *             - name: The name of the player.
     *             - isInitiator: A boolean indicating if the player is the initiator.
     *             - inboxFile: The path to the inbox file.
     *             - outboxFile: The path to the outbox file.
     */
    public static void main(String[] args) {
        System.out.println("MultiProcessPlayer started with args: " + String.join(", ", args));

        if (args.length < 4) {
            System.err.println("Usage: MultiProcessPlayer <name> <isInitiator> <inboxFile> <outboxFile>");
            System.exit(1);
        }

        String name = args[0];
        boolean isInitiator = Boolean.parseBoolean(args[1]);
        String inboxFile = args[2];
        String outboxFile = args[3];

        MultiProcessPlayer player = new MultiProcessPlayer(name, isInitiator, inboxFile, outboxFile);
        new Thread(player).start();
    }
}
