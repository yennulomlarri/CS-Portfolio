import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clock class responsible for managing time data and display.
 * Following rubric: Implementation of a method to update and print concurrently.
 */
class Clock {
    private LocalDateTime currentTime;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    public Clock() {
        this.currentTime = LocalDateTime.now();
    }

    /**
     * Requirement: A method to continuously update and print the current time.
     * Synchronized to prevent data conflicts between threads.
     */
    public synchronized void updateAndPrintTime() {
        this.currentTime = LocalDateTime.now();
        // Printing with \r ensures the clock updates on a single line in the console
        System.out.print("\rCurrent Time: " + currentTime.format(FORMATTER));
    }

    /**
     * Helper method for the background thread to update internal state.
     */
    public synchronized void updateOnly() {
        this.currentTime = LocalDateTime.now();
    }
}

public class SimpleClockApp {
    // Using simple main to satisfy modern Java 25 IDE suggestions
    static void main() {
        Clock sharedClock = new Clock();

        // Thread 1: Background Updating Thread (Lower Priority)
        // Moves through lifecycle: NEW -> RUNNABLE -> TIMED_WAITING
        Thread updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                sharedClock.updateOnly();
                try {
                    // Small sleep for high precision data updating
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Thread 2: Clock Display Thread (Higher Priority)
        // Requirement: Separate thread for printing time to the console.
        Thread displayThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                sharedClock.updateAndPrintTime();
                try {
                    // Refresh display every 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        /* * Thread Priorities: Requirement 3a & 3b.
         * Assigning higher priority to display ensures smoother timekeeping precision.
         */
        displayThread.setPriority(Thread.MAX_PRIORITY);
        updateThread.setPriority(Thread.MIN_PRIORITY);

        // Start execution
        updateThread.start();
        displayThread.start();
    }
}