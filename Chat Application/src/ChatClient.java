import java.io.*;
import java.net.*;

/**
 * ChatClient - Client application for the chat system
 *
 * This client connects to the chat server, allows users to send messages,
 * and displays messages received from other users.
 *
 * @author Mateiyendou Kombat
 * @version 2.0
 */
public class ChatClient {
    private static final String DEFAULT_SERVER = "localhost";
    private static final int DEFAULT_PORT = 5000;
    private static final int CONNECTION_TIMEOUT = 5000; // 5 seconds timeout - NEW

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private java.util.Scanner scanner;
    private boolean connected = true;

    public static void main(String[] args) {
        String serverAddress = DEFAULT_SERVER;
        int serverPort = DEFAULT_PORT;

        if (args.length > 0) {
            serverAddress = args[0];
        }
        if (args.length > 1) {
            try {
                serverPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number. Using default: " + DEFAULT_PORT);
            }
        }

        ChatClient client = new ChatClient();
        client.start(serverAddress, serverPort);
    }

    public void start(String serverAddress, int serverPort) {
        try {
            System.out.println("==========================================");
            System.out.println("     CHAT CLIENT");
            System.out.println("==========================================");
            System.out.println("Connecting to server: " + serverAddress + ":" + serverPort);
            System.out.println("Connection timeout: " + CONNECTION_TIMEOUT + "ms");

            // IMPROVED: Socket connection with timeout
            socket = new Socket();
            socket.connect(new InetSocketAddress(serverAddress, serverPort), CONNECTION_TIMEOUT);

            System.out.println("Connected successfully!\n");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            scanner = new java.util.Scanner(System.in);

            // Display available commands
            System.out.println("Type '/help' for available commands\n");

            Thread receiveThread = new Thread(this::receiveMessages);
            receiveThread.setDaemon(true);
            receiveThread.start();

            sendMessages();

        } catch (SocketTimeoutException e) {
            System.err.println("\n[ERROR] Connection timeout after " + CONNECTION_TIMEOUT + "ms");
            System.err.println("Server at " + serverAddress + ":" + serverPort + " is not responding");
        } catch (IOException e) {
            System.err.println("\n[ERROR] Failed to connect to server: " + e.getMessage());
            System.err.println("Please check that the server is running at " +
                    serverAddress + ":" + serverPort);
        } finally {
            cleanup();
        }
    }

    private void receiveMessages() {
        try {
            String serverMessage;
            while (connected && (serverMessage = in.readLine()) != null) {
                System.out.println(serverMessage);
            }
        } catch (IOException e) {
            if (connected) {
                System.out.println("\n[DISCONNECTED] Connection to server lost.");
            }
        }
    }

    private void sendMessages() {
        System.out.println("Start typing your messages:\n");

        while (connected) {
            String message = scanner.nextLine();

            if (message.equalsIgnoreCase("/quit")) {
                out.println("/quit");
                connected = false;
                System.out.println("\nDisconnecting from chat...");
                break;
            }

            out.println(message);
        }
    }

    private void cleanup() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (scanner != null) {
                scanner.close();
            }
            System.out.println("Cleanup completed. Goodbye!");
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }
}