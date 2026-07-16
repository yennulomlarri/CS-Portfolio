
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A static class that acts as a central data repository for the application.
 * It holds observable lists, allowing the UI to update automatically when data changes.
 */
public class DataManager {
    private static final ObservableList<Student> students = FXCollections.observableArrayList();
    private static final ObservableList<Course> courses = FXCollections.observableArrayList();

    static {
        courses.add(new Course("CS101", "Intro to Computer Science"));
        courses.add(new Course("MTH201", "Calculus I"));
        courses.add(new Course("ENG101", "English Composition"));

        Student alice = new Student("S001", "Alice", "Wonderland");
        alice.enrollInCourse(courses.getFirst());
        alice.assignGrade(courses.getFirst(), 95);

        Student bob = new Student("S002", "Bob", "Builder");
        bob.enrollInCourse(courses.getFirst());
        bob.enrollInCourse(courses.get(1));

        students.addAll(alice, bob);
    }

    public static ObservableList<Student> getStudents() { return students; }
    public static ObservableList<Course> getCourses() { return courses; }

    public static void addStudent(Student student) { students.add(student); }
    public static void addCourse(Course course) { courses.add(course); }

    public static void removeStudent(Student student) {
        if (student != null) {
            students.remove(student);
        }
    }

    public static void removeCourse(Course course) {
        if (course != null) {
            courses.remove(course);
            for (Student student : students) {
                student.getEnrolledCoursesWithGrades().remove(course);
            }
        }
    }
}