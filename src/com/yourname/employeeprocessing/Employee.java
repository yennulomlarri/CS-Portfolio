package com.yourname.employeeprocessing;

/**
 * Represents an employee with attributes such as name, age, department, and salary.
 * This class is a "record", a special, concise class in modern Java for immutable data carriers.
 * The compiler automatically generates the constructor, private final fields, getters,
 * equals(), hashCode(), and toString() methods.
 */
public record Employee(String name, int age, String department, double salary) {
    // The record declaration above is all that's needed.
    // We can optionally override methods for custom behavior, like toString() for custom formatting.
    @Override
    public String toString() {
        return String.format("Employee[Name=%-15s, Age=%d, Dept=%-12s, Salary=$%,.2f]",
                name, age, department, salary);
    }
}