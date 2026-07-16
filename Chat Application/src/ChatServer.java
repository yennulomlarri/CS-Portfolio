import java.io.*;
import java.net.*;
import java.util.*;

/**
 * ChatServer - Central server for the chat application
 *
 * This server manages multiple client connections, assigns unique IDs,
 * and broadcasts messages to all connected clients.
 *
 * @author Mateiyendou Kombat
 * @version 2.0
 */
public class ChatServer {
    private static final int PORT = 5000;
    private static int userIdCounter = 1;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     CHAT SERVER STARTED");
        System.out.println("========================================");
        System.out.println("Server listening on port: " + PORT);
        System.out.println("Waiting for clients to connect...\n");

        // Add shutdown hook for graceful server termination
        addShutdownHook();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {
                // Accept new client connection
                Socket socket = serverSocket.accept();

                // Assign unique user ID
                int userId = userIdCounter++;

                // Get client information for logging
                String clientAddress = socket.getInetAddress().getHostAddress();

                // Create client handler
                ClientHandler client = new ClientHandler(socket, userId);
                clients.add(client);

                // Start client thread
                new Thread(client).start();

                // Log connection
                System.out.println("[SERVER] User " + userId + " connected from " + clientAddress);
                System.out.println("[SERVER] Total connected users: " + clients.size() + "\n");

                // Broadcast join message to all other users
                String joinMessage = "** User " + userId + " has joined the chat **";
                broadcast(joinMessage, null); // null sender means everyone gets it
            }

        } catch (IOException e) {
            System.err.println("[SERVER ERROR] " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add shutdown hook to handle graceful server termination
     */
    private static void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[SERVER] Shutting down gracefully...");
            String shutdownMessage = "*** Server is shutting down. Goodbye! ***";

            // Notify all connected clients
            for (ClientHandler client : clients) {
                client.sendMessage(shutdownMessage);
            }

            // Small delay to allow messages to be sent
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("[SERVER] Shutdown complete.");
        }));
    }

    /**
     * Broadcast a message to all connected clients except the sender
     *
     * @param message The message to broadcast
     * @param sender The client who sent the message (null = send to everyone)
     */
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            // Send to everyone if sender is null, otherwise skip sender
            if (sender == null || client != sender) {
                client.sendMessage(message);
            }
        }

        // Log broadcast to server console - FIXED: removed duplicate prefix
        if (sender != null) {
            System.out.println("[BROADCAST] " + message);
        } else {
            System.out.println("[BROADCAST] " + message);
        }
    }

    /**
     * Remove a client from the active list when they disconnect
     *
     * @param client The client to remove
     */
    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("[SERVER] User " + client.getUserId() + " disconnected");
        System.out.println("[SERVER] Total connected users: " + clients.size() + "\n");

        // Notify remaining users
        String leaveMessage = "** User " + client.getUserId() + " has left the chat **";
        broadcast(leaveMessage, null);
    }

    /**
     * Get the current number of connected clients
     *
     * @return Number of active clients
     */
    public static int getClientCount() {
        return clients.size();
    }

    /**
     * Get list of online users as a formatted string
     *
     * @return String with list of online user IDs
     */
    public static String getOnlineUsersList() {
        StringBuilder users = new StringBuilder("Online users: ");
        for (int i = 0; i < clients.size(); i++) {
            users.append("User ").append(clients.get(i).getUserId());
            if (i < clients.size() - 1) {
                users.append(", ");
            }
        }
        return users.toString();
    }
}