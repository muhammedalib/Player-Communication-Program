package Player;

/**
 * Main class to run the Player communication program.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("multi")) {
            MultiProcessManager.runMultiProcessMode();
        } else {
            System.out.println("Running single-process version...");
            SingleProcessManager.runSingleProcessMode();
        }
    }
}
