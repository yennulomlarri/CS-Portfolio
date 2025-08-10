package com.yourname.employeeprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Main application class to demonstrate processing a list of Employee objects
 * using the Java Function interface and the Stream API.
 * This version is updated to use modern Java features like records and Stream.toList().
 */
public class EmployeeStreamProcessing {

    public static void main(String[] args) {

        // Requirement 1: Store the dataset in a collection.
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("Alice Smith", 28, "Engineering", 80000.00),
                new Employee("Bob Johnson", 35, "Marketing", 90000.00),
                new Employee("Charlie Brown", 42, "Engineering", 115000.00),
                new Employee("Diana Prince", 24, "Sales", 65000.00),
                new Employee("Ethan Hunt", 38, "Marketing", 105000.00),
                new Employee("Fiona Glenanne", 31, "Sales", 75000.00) // The IDE typo warning would now be resolved.
        ));

        System.out.println("--- Original Employee Dataset ---");
        employees.forEach(System.out::println);
        printSeparator();

        // Requirement 2: Define a Function.
        // The lambda now uses the record's accessor methods: .name() and .department()
        Function<Employee, String> nameAndDeptFunction =
                employee -> employee.name() + " (" + employee.department() + ")";

        // Requirement 3: Generate a new collection.
        // CORRECTED: .collect(Collectors.toList()) is replaced with the more modern .toList()
        List<String> employeeSummaries = employees.stream()
                .map(nameAndDeptFunction)
                .toList(); // This returns an unmodifiable list, which is safer.

        System.out.println("--- Employee Summaries (Generated via Function and Stream.map) ---");
        employeeSummaries.forEach(System.out::println);
        printSeparator();

        // Requirement 4: Find the average salary.
        // CORRECTED: The method reference now uses the record's accessor: Employee::salary
        double averageSalaryAll = employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0.0);

        System.out.println("--- Average Salary of All Employees ---");
        System.out.printf("Average Salary: $%,.2f\n", averageSalaryAll);
        printSeparator();

        // Requirement 5: Filter employees by age.
        // The lambda now uses the record's accessor method: .age()
        // CORRECTED: Replaced .collect(Collectors.toList()) with .toList()
        List<Employee> seniorEmployees = employees.stream()
                .filter(employee -> employee.age() > 30)
                .toList();

        System.out.println("--- Employees Older Than 30 ---");
        seniorEmployees.forEach(System.out::println);
        printSeparator();

        // Additional Feature: Grouping by department.
        // CORRECTED: Method references updated for the record's accessors.
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department, // Use record accessor method reference
                        Collectors.averagingDouble(Employee::salary) // Use record accessor method reference
                ));

        System.out.println("--- (Bonus) Average Salary by Department ---");
        avgSalaryByDept.forEach((department, avgSalary) ->
                System.out.printf("Department: %-12s | Average Salary: $%,.2f\n", department, avgSalary)
        );
        printSeparator();
    }

    /**
     * A simple helper method to print a separator line to make console output more readable.
     */
    private static void printSeparator() {
        System.out.println("\n========================================================\n");
    }
}