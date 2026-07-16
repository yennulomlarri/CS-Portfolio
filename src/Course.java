/**
 * Represents a single course with an ID and a name.
 * This is a standard class to hold course data, with setters to allow for modification.
 */
// The @SuppressWarnings("ClassCanBeRecord") has been removed.
public class Course {
    private String courseId;
    private String courseName;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Overrides the default toString() to provide a more readable format for ComboBoxes.
     */
    @Override
    public String toString() {
        return courseName + " (" + courseId + ")";
    }
}