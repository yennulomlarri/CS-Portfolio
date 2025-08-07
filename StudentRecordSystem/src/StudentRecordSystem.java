import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentRecordSystem {

    // Made FINAL as its value never changes.
    private static final int MAX_STUDENTS = 100;

    // All these fields are now FINAL to remove IntelliJ warnings.
    private static final String[] studentNames = new String[MAX_STUDENTS];
    private static final int[] studentIDs = new int[MAX_STUDENTS];
    private static final int[] studentAges = new int[MAX_STUDENTS];
    private static final String[] studentGrades = new String[MAX_STUDENTS];

    private static int studentCount = 0;
    // The scanner is also final because we only create one.
    private static final Scanner scanner = new Scanner(System.in);

    // main MUST be public.
    public static void main(String[] args) {
        System.out.println("Welcome to the Student Record Management System!");

        while (true) {
            displayMenu();
            System.out.print("\nEnter your choice: ");
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    viewStudentDetails();
                    break;
                case 4:
                    viewAllStudents();
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    // This method is only called from main, so it should be PRIVATE.
    private static void displayMenu() {
        System.out.println("\n--- Administrator Menu ---");
        System.out.println("1. Add a New Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. View Specific Student Details");
        System.out.println("4. View All Students");
        System.out.println("5. Exit");
    }

    // All the following helper methods are also only used internally, so they
    // should be PRIVATE.
    private static void addStudent() {
        System.out.println("\n--- Add a New Student ---");

        if (studentCount >= MAX_STUDENTS) {
            System.out.println("Error: System is at full capacity. Cannot add more students.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        int id = getIntegerInput("Enter Student ID (must be unique): ");
        if (findStudentIndexById(id) != -1) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }

        int age = getIntegerInput("Enter Student Age: ");
        System.out.print("Enter Student Grade (e.g., 'A', 'B+', 'C'): ");
        String grade = scanner.next();
        scanner.nextLine(); // Consume the leftover newline

        studentNames[studentCount] = name;
        studentIDs[studentCount] = id;
        studentAges[studentCount] = age;
        studentGrades[studentCount] = grade;

        studentCount++;

        System.out.println("\nSuccess: Student '" + name + "' has been added.");
    }

    /**
     * THIS IS THE FULLY CORRECTED UPDATE METHOD.
     * It now correctly prompts the user to update the grade.
     */
    private static void updateStudent() {
        System.out.println("\n--- Update Student Information ---");
        if (studentCount == 0) {
            System.out.println("No students in the system to update.");
            return;
        }

        int idToUpdate = getIntegerInput("Enter the ID of the student to update: ");
        int studentIndex = findStudentIndexById(idToUpdate);

        if (studentIndex == -1) {
            System.out.println("Error: Student with ID " + idToUpdate + " not found.");
            return;
        }

        System.out.println("Updating details for student: " + studentNames[studentIndex]);

        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();
        studentNames[studentIndex] = newName;

        studentAges[studentIndex] = getIntegerInput("Enter new Age: ");

        // THE MISSING PART IS NOW ADDED HERE:
        System.out.print("Enter new Grade: ");
        studentGrades[studentIndex] = scanner.next();
        scanner.nextLine(); // Consume the leftover newline

        System.out.println("\nSuccess: Student information has been updated.");
    }


    private static void viewStudentDetails() {
        System.out.println("\n--- View Specific Student Details ---");
        if (studentCount == 0) {
            System.out.println("No students in the system to view.");
            return;
        }

        int idToView = getIntegerInput("Enter the ID of the student to view: ");
        int studentIndex = findStudentIndexById(idToView);

        if (studentIndex == -1) {
            System.out.println("Error: Student with ID " + idToView + " not found.");
            return;
        }

        System.out.println("\n--- Student Record ---");
        System.out.println("ID:    " + studentIDs[studentIndex]);
        System.out.println("Name:  " + studentNames[studentIndex]);
        System.out.println("Age:   " + studentAges[studentIndex]);
        System.out.println("Grade: " + studentGrades[studentIndex]);
    }

    /**
     * This new method displays a list of all students in the system.
     */
    private static void viewAllStudents() {
        System.out.println("\n--- All Student Records ---");
        if (studentCount == 0) {
            System.out.println("There are no students in the system.");
            return;
        }

        System.out.printf("%-10s %-25s %-5s %-10s\n", "ID", "Name", "Age", "Grade");
        System.out.println("-----------------------------------------------------");

        for (int i = 0; i < studentCount; i++) {
            System.out.printf("%-10d %-25s %-5d %-10s\n",
                    studentIDs[i], studentNames[i], studentAges[i], studentGrades[i]);
        }
    }


    private static int findStudentIndexById(int studentId) {
        for (int i = 0; i < studentCount; i++) {
            if (studentIDs[i] == studentId) {
                return i;
            }
        }
        return -1;
    }

    private static int getIntegerInput(String prompt) {
        System.out.print(prompt);
        return getIntegerInput();
    }

    private static int getIntegerInput() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a whole number.");
                System.out.print("Try again: ");
                scanner.nextLine();
            }
        }
    }
}