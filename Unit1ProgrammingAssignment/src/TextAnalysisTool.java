import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

/**
 * TextAnalysisTool performs quantitative and qualitative analysis on text.
 * It calculates word counts, frequencies, and unique lexical items.
 *
 * @author [Your Name]
 * @version 1.0
 */
public class TextAnalysisTool {

    private String textData;

    /**
     * Entry point of the application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        TextAnalysisTool tool = new TextAnalysisTool();
        tool.execute();
    }

    /**
     * Coordinates the program flow and user interaction.
     */
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Text Analysis Utility v1.0");

        // Input Validation: Ensuring text is not empty
        do {
            System.out.println("Please enter a paragraph of text:");
            textData = scanner.nextLine();
            if (textData.trim().isEmpty()) {
                System.out.println("Error: Input cannot be empty.");
            }
        } while (textData.trim().isEmpty());

        performCalculations(scanner);
        scanner.close();
    }

    /**
     * Handles the logic for character and word operations.
     * @param scanner The scanner for user input.
     */
    private void performCalculations(Scanner scanner) {
        // Character and Word Counts
        int charCount = textData.length();
        String[] words = textData.trim().split("\\s+");

        System.out.println("\n--- Analysis Results ---");
        System.out.println("Total Characters: " + charCount);
        System.out.println("Total Words: " + words.length);

        // Most Common Character
        System.out.println("Most Common Character: " + findCommonChar());

        // Character Frequency
        System.out.println("\nEnter a character to check its frequency:");
        String charInput = scanner.nextLine();
        if (!charInput.isEmpty()) {
            char target = charInput.toLowerCase().charAt(0);
            System.out.println("Frequency of '" + target + "': "
                    + getCharFreq(target));
        }

        // Word Frequency
        System.out.println("\nEnter a word to check its frequency:");
        String targetWord = scanner.nextLine();
        System.out.println("Frequency of \"" + targetWord + "\": "
                + getWordFreq(words, targetWord));

        // Unique Words
        System.out.println("Unique Words Count: " + countUnique(words));
    }

    /**
     * Determines the most frequent character in the input string.
     * @return The character appearing most often.
     */
    private char findCommonChar() {
        int[] counts = new int[256];
        int max = -1;
        char common = ' ';
        for (char c : textData.toLowerCase().toCharArray()) {
            if (c != ' ') {
                counts[c]++;
                if (counts[c] > max) {
                    max = counts[c];
                    common = c;
                }
            }
        }
        return common;
    }

    /**
     * Counts occurrences of a character, case-insensitive.
     * @param target The character to search for.
     * @return The frequency count.
     */
    private int getCharFreq(char target) {
        int count = 0;
        for (char c : textData.toLowerCase().toCharArray()) {
            if (c == target) count++;
        }
        return count;
    }

    /**
     * Counts occurrences of a word, case-insensitive.
     * @param words The array of tokens.
     * @param target The word to search for.
     * @return The frequency count.
     */
    private int getWordFreq(String[] words, String target) {
        int count = 0;
        for (String w : words) {
            if (w.equalsIgnoreCase(target)) count++;
        }
        return count;
    }

    /**
     * Uses a HashSet to determine unique word count.
     * @param words The array of tokens.
     * @return The number of unique words.
     */
    private int countUnique(String[] words) {
        Set<String> uniqueSet = new HashSet<>();
        for (String w : words) {
            uniqueSet.add(w.toLowerCase().replaceAll("[^a-z]", ""));
        }
        return uniqueSet.size();
    }
}