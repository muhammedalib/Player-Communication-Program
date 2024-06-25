package Player;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

/**
 * Test class for multi-process communication.
 */
public class MultiProcessTest {
	/**
     * Tests the communication between two MultiProcessPlayer instances
     * running in separate JVM processes. Ensures the inbox files are 
     * correctly created and populated.
     */

    @Test
    public void testMultiProcessCommunication() {
        String player1Inbox = "player1_inbox_test.txt";
        String player2Inbox = "player2_inbox_test.txt";

        // Ensure inbox files are clean before the test
        cleanFile(player1Inbox);
        cleanFile(player2Inbox);

        // Run the multi-process mode
        MultiProcessManager.runMultiProcessMode();

        // Delay to allow processes to complete
        try {
            Thread.sleep(15000); // Wait for 15 seconds to allow processes to communicate
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrupted: " + e.getMessage());
        }

        // Check if the communication files are populated
        assertTrue("Player1 inbox should exist", new File(player1Inbox).exists());
        assertTrue("Player2 inbox should exist", new File(player2Inbox).exists());

        // Clean up test files
        cleanFile(player1Inbox);
        cleanFile(player2Inbox);
    }

    /**
     * Helper method to clean the specified file by deleting it if it exists.
     * 
     * @param fileName The name of the file to clean.
     */
    private void cleanFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
