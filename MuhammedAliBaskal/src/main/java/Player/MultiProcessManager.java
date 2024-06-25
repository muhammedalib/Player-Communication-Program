package Player;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * MultiProcessManager class responsible for managing player processes
 * in multi-process mode. Each player runs in a separate JVM process.
 */
public class MultiProcessManager {

    /**
     * Runs both players in separate JVM processes.
     */
    public static void runMultiProcessMode() {
        Process player1Process = startPlayerProcess("Player1", true, "player1_inbox_test.txt", "player2_inbox_test.txt");
        Process player2Process = startPlayerProcess("Player2", false, "player2_inbox_test.txt", "player1_inbox_test.txt");

        try {
            if (player1Process != null) {
                player1Process.waitFor();
            }
            if (player2Process != null) {
                player2Process.waitFor();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Starts a player process with the given parameters.
     * @param name The name of the player.
     * @param isInitiator Flag indicating if the player is the initiator.
     * @param inboxFile The inbox file for the player.
     * @param outboxFile The outbox file for the player.
     * @return The process object for the started player.
     */
    private static Process startPlayerProcess(String name, boolean isInitiator, String inboxFile, String outboxFile) {
        try {
            // Get the path to the java executable
            String javaHome = System.getProperty("java.home");
            String javaBin = Paths.get(javaHome, "bin", "java").toString();
            // Set the classpath to the target/classes directory
            String classpath = Paths.get(System.getProperty("user.dir"), "target", "classes").toString();
            // Build the command to run the player process
            String className = "Player.MultiProcessPlayer";
            ProcessBuilder processBuilder = new ProcessBuilder(javaBin, "-cp", classpath, className,
                    name, Boolean.toString(isInitiator), inboxFile, outboxFile);
            processBuilder.inheritIO(); // This line ensures the output of the process is displayed in the terminal
            System.out.println("Starting process: " + processBuilder.command());
            return processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
