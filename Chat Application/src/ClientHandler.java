import java.io.*;
import java.net.*;

/**
 * ClientHandler - Handles communication with a single client
 *
 * Each connected client runs in its own thread, allowing multiple
 * clients to communicate concurrently.
 *
 * @author Mateiyendou Kombat
 * @version 2.0
 */
public class ClientHandler implements Runnable {
    private Socket socket;
    private int userId;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Constructor for ClientHandler
     *
     * @param socket The client socket
     * @param userId The unique ID assigned to this client
     */
    public ClientHandler(Socket socket, int userId) {
        this.socket = socket;
        this.userId = userId;
    }

    /**
     * Get the user ID of this client
     *
     * @return The user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Send a message to this client
     *
     * @param message The message to send
     */
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    @Override
    public void run() {
        try {
            // Initialize input and output streams
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Send welcome message to the client
            sendWelcomeMessage();

            String message;

            // Continuously read messages from this client
            while ((message = in.readLine()) != null) {

                // Check for quit command
                if (message.equalsIgnoreCase("/quit")) {
                    System.out.println("[SERVER] User " + userId + " requested to quit");
                    break;
                }

                // Check for users command - NEW FEATURE
                if (message.equalsIgnoreCase("/users")) {
                    sendMessage(ChatServer.getOnlineUsersList());
                    continue;
                }

                // Check for help command - NEW FEATURE
                if (message.equalsIgnoreCase("/help")) {
                    sendHelpMessage();
                    continue;
                }

                // Format message with user ID and broadcast
                String formattedMessage = "User " + userId + ": " + message;
                ChatServer.broadcast(formattedMessage, this);
            }

        } catch (IOException e) {
            // Client disconnected unexpectedly
            System.out.println("[SERVER] User " + userId + " connection lost: " + e.getMessage());
        } finally {
            // Clean up resources
            cleanup();
        }
    }

    /**
     * Send a formatted welcome message to the client
     */
    private void sendWelcomeMessage() {
        out.println("==========================================");
        out.println("     WELCOME TO THE CHAT SERVER");
        out.println("==========================================");
        out.println("You are connected as User " + userId);
        out.println("Total users online: " + ChatServer.getClientCount());
        out.println("------------------------------------------");
        out.println("Available Commands:");
        out.println("  • Type any message to chat");
        out.println("  • '/users' - See who is online");
        out.println("  • '/help' - Show this help message");
        out.println("  • '/quit' - Exit the chat");
        out.println("==========================================\n");
    }

    /**
     * Send help message with available commands - NEW METHOD
     */
    private void sendHelpMessage() {
        out.println("----------- HELP -----------");
        out.println("Type any message to broadcast to all users");
        out.println("/users - Show list of online users");
        out.println("/help  - Show this help message");
        out.println("/quit  - Disconnect from chat");
        out.println("----------------------------");
    }

    /**
     * Clean up resources and remove client from server
     */
    private void cleanup() {
        try {
            // Remove client from server's active list
            ChatServer.removeClient(this);

            // Close socket if still open
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to close socket for User " + userId);
        }
    }
}