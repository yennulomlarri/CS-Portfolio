import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Represents a single student with personal details and enrolled courses/grades.
 * Uses JavaFX properties for seamless integration with TableView.
 */
public class Student {
    private final SimpleStringProperty studentId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final ObservableMap<Course, Integer> enrolledCoursesWithGrades;

    public Student(String studentId, String firstName, String lastName) {
        this.studentId = new SimpleStringProperty(studentId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.enrolledCoursesWithGrades = FXCollections.observableHashMap();
    }

    // --- Standard Getters ---
    public String getStudentId() { return studentId.get(); }
    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public ObservableMap<Course, Integer> getEnrolledCoursesWithGrades() {
        return enrolledCoursesWithGrades;
    }

    // --- Standard Setters (For Update Functionality) ---
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }

    // --- JavaFX Property Getters (Used by TableView's PropertyValueFactory) ---
    @SuppressWarnings("unused")
    public SimpleStringProperty studentIdProperty() { return studentId; }
    @SuppressWarnings("unused")
    public SimpleStringProperty firstNameProperty() { return firstName; }
    @SuppressWarnings("unused")
    public SimpleStringProperty lastNameProperty() { return lastName; }

    // --- Core Functionality ---
    public void enrollInCourse(Course course) {
        enrolledCoursesWithGrades.put(course, -1);
    }

    public void assignGrade(Course course, int grade) {
        if (enrolledCoursesWithGrades.containsKey(course)) {
            enrolledCoursesWithGrades.put(course, grade);
        }
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " (" + getStudentId() + ")";
    }
}