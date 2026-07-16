package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // --- 1. Setup Phase ---
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int totalQuestions = 5;

        System.out.println("Welcome to the Java Quiz Game!");
        System.out.println("Answer the following 5 questions by typing A, B, C, or D.\n");

        // --- 2. Question Section ---

        // Question 1: Using an if-else statement
        System.out.println("Question 1: What is the capital of Japan?");
        System.out.println("  A. Seoul");
        System.out.println("  B. Beijing");
        System.out.println("  C. Tokyo");
        System.out.println("  D. Bangkok");
        System.out.print("Your answer: ");
        String answer1 = scanner.nextLine().toUpperCase();
        if (answer1.equals("C")) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was C. Tokyo.");
        }
        System.out.println("----------------------------------------");

        // Question 2: Using an if-else statement
        System.out.println("Question 2: Which planet is known as the Red Planet?");
        System.out.println("  A. Venus");
        System.out.println("  B. Jupiter");
        System.out.println("  C. Saturn");
        System.out.println("  D. Mars");
        System.out.print("Your answer: ");
        String answer2 = scanner.nextLine().toUpperCase();
        if (answer2.equals("D")) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was D. Mars.");
        }
        System.out.println("----------------------------------------");

        // Question 3: Using a switch-case statement
        System.out.println("Question 3: What is the main ingredient in guacamole?");
        System.out.println("  A. Tomato");
        System.out.println("  B. Avocado");
        System.out.println("  C. Onion");
        System.out.println("  D. Lime");
        System.out.print("Your answer: ");
        String answer3 = scanner.nextLine().toUpperCase();
        switch (answer3) {
            case "B":
                System.out.println("Correct!");
                score++;
                break;
            case "A":
            case "C":
            case "D":
                System.out.println("Incorrect. The correct answer was B. Avocado.");
                break;
            default:
                System.out.println("Invalid input. The correct answer was B. Avocado.");
                break;
        }
        System.out.println("----------------------------------------");

        // Question 4: Using a switch-case statement
        System.out.println("Question 4: In Java, which keyword is used to create a new object?");
        System.out.println("  A. create");
        System.out.println("  B. new");
        System.out.println("  C. object");
        System.out.println("  D. make");
        System.out.print("Your answer: ");
        String answer4 = scanner.nextLine().toUpperCase();
        switch (answer4) {
            case "B":
                System.out.println("Correct!");
                score++;
                break;
            case "A":
            case "C":
            case "D":
                System.out.println("Incorrect. The correct answer was B. new.");
                break;
            default:
                System.out.println("Invalid input. The correct answer was B. new.");
                break;
        }
        System.out.println("----------------------------------------");

        // Question 5: Using an if-else statement
        System.out.println("Question 5: What is the chemical symbol for Gold?");
        System.out.println("  A. Ag");
        System.out.println("  B. Au");
        System.out.println("  C. G");
        System.out.println("  D. Go");
        System.out.print("Your answer: ");
        String answer5 = scanner.nextLine().toUpperCase();
        if (answer5.equals("B")) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was B. Au.");
        }
        System.out.println("----------------------------------------");

        // --- 3. Final Score Calculation and Display ---
        double finalScorePercentage = ((double) score / totalQuestions) * 100;

        System.out.println("\n--- Quiz Complete! ---");
        System.out.println("You answered " + score + " out of " + totalQuestions + " questions correctly.");
        System.out.println("Your final score is: " + finalScorePercentage + "%");

        // --- 4. Cleanup ---
        scanner.close();
    }
}