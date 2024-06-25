package Player;

/**
 * SingleProcessManager class responsible for managing player processes
 * in single-process mode. Both players run in the same JVM process.
 */
public class SingleProcessManager {

    /**
     * Runs both players in the same JVM process. Initializes players,
     * sets up their communication channels, and starts their threads.
     */
    public static void runSingleProcessMode() {
        SingleProcessPlayer player1 = new SingleProcessPlayer("Player1", true);
        SingleProcessPlayer player2 = new SingleProcessPlayer("Player2", false);

        // Set up communication channels
        player1.setOutbox(player2.getInbox());
        player2.setOutbox(player1.getInbox());

        Thread player1Thread = new Thread(player1);
        Thread player2Thread = new Thread(player2);

        player1Thread.start();
        player2Thread.start();

        try {
            // Waits for this thread to die. 
            // This means the current thread (main thread) will wait until player1Thread and player2Thread finish executing.
            player1Thread.join();
            player2Thread.join();
        } catch (InterruptedException e) {
            // If the current thread is interrupted while waiting
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        System.out.println("Single process mode: Communication ended.");
    }
}
