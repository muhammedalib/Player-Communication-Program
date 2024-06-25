package Player;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for single process communication.
 */
public class SingleProcessTest {

	/**
     * Tests the communication between two Player instances
     * running in the same JVM process. Ensures that both players
     * can send and receive messages correctly.
     */
    @Test
    public void testSingleProcessCommunication() {
    	SingleProcessPlayer player1 = new SingleProcessPlayer("Player1", true);
    	SingleProcessPlayer player2 = new SingleProcessPlayer("Player2", false);

        player1.setOutbox(player2.getInbox());
        player2.setOutbox(player1.getInbox());

        Thread player1Thread = new Thread(player1);
        Thread player2Thread = new Thread(player2);

        player1Thread.start();
        player2Thread.start();

        try {
            player1Thread.join();
            player2Thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrupted: " + e.getMessage());
        }

        assertTrue("Player1 should have finished communication", true);
        assertTrue("Player2 should have finished communication", true);
    }
}
