package com.weatherapp.service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HistoryManager {
    private static final String HISTORY_FILE = "weather_history.txt";
    private List<String> historyEntries;

    public HistoryManager() {
        historyEntries = new ArrayList<>();
        loadHistory();
    }

    public void addHistoryEntry(String city) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = city + " - " + timestamp;
        historyEntries.add(0, entry); // Add to beginning

        // Keep last 20 entries
        while (historyEntries.size() > 20) {
            historyEntries.remove(historyEntries.size() - 1);
        }
        saveHistory();
    }

    public List<String> getHistoryEntries() {
        return new ArrayList<>(historyEntries);
    }

    public void clearHistory() {
        historyEntries.clear();
        saveHistory();
    }

    private void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (String entry : historyEntries) {
                writer.println(entry);
            }
        } catch (IOException e) {
            System.err.println("Failed to save history: " + e.getMessage());
        }
    }

    private void loadHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyEntries.add(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to load history: " + e.getMessage());
        }
    }
}