import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

// NOTE: All unused imports have been removed to resolve the final warnings.

public class StudentManagementSystem extends Application {

    private final TableView<Student> studentTable = new TableView<>();
    private final TableView<Map.Entry<Course, Integer>> gradeTable = new TableView<>();
    private final ComboBox<Course> courseComboBox = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Management System");
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        TabPane tabPane = new TabPane();
        Tab studentTab = new Tab("Student Management", createStudentManagementTab());
        Tab enrollmentTab = new Tab("Course Enrollment", createCourseEnrollmentTab());
        Tab gradeTab = new Tab("Grade Management", createGradeManagementTab());

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(studentTab, enrollmentTab, gradeTab);

        root.setCenter(tabPane);
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @SuppressWarnings("unchecked")
    private VBox createStudentManagementTab() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TableColumn<Student, String> idCol = new TableColumn<>("Student ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        studentTable.getColumns().addAll(idCol, firstNameCol, lastNameCol);
        studentTable.setItems(DataManager.getStudents());

        layout.getChildren().addAll(new Label("All Students:"), studentTable, createStudentManagementButtons());
        return layout;
    }

    private HBox createStudentManagementButtons() {
        Button addStudentBtn = new Button("Add New Student");
        addStudentBtn.setOnAction(_ -> handleAddStudent());

        Button updateStudentBtn = new Button("Update Selected Student");
        updateStudentBtn.disableProperty().bind(studentTable.getSelectionModel().selectedItemProperty().isNull());
        updateStudentBtn.setOnAction(_ -> {
            Student selected = studentTable.getSelectionModel().getSelectedItem();
            if (selected != null) handleUpdateStudent(selected);
        });

        Button deleteStudentBtn = new Button("Delete Selected Student");
        deleteStudentBtn.disableProperty().bind(studentTable.getSelectionModel().selectedItemProperty().isNull());
        deleteStudentBtn.setOnAction(_ -> {
            Student selected = studentTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + selected.getFirstName() + "?", ButtonType.YES, ButtonType.NO);
                confirmation.setTitle("Confirm Deletion");
                confirmation.setHeaderText("Delete Student");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        DataManager.removeStudent(selected);
                        showSuccess("Student deleted successfully.");
                    }
                });
            }
        });
        return new HBox(10, addStudentBtn, updateStudentBtn, deleteStudentBtn);
    }

    private VBox createCourseEnrollmentTab() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        this.courseComboBox.setItems(DataManager.getCourses());
        this.courseComboBox.setPromptText("Select a Course to Manage or Enroll");

        ComboBox<Student> studentComboBox = new ComboBox<>(DataManager.getStudents());
        studentComboBox.setPromptText("Select a Student to Enroll");

        Button addCourseBtn = new Button("Add New Course");
        addCourseBtn.setOnAction(_ -> handleAddCourse());

        Button updateCourseBtn = new Button("Update Selected Course");
        updateCourseBtn.disableProperty().bind(courseComboBox.getSelectionModel().selectedItemProperty().isNull());
        updateCourseBtn.setOnAction(_ -> {
            Course selected = courseComboBox.getValue();
            if (selected != null) handleUpdateCourse(selected);
        });

        Button removeCourseBtn = new Button("Remove Selected Course");
        removeCourseBtn.disableProperty().bind(courseComboBox.getSelectionModel().selectedItemProperty().isNull());
        removeCourseBtn.setOnAction(_ -> {
            Course selected = courseComboBox.getValue();
            if (selected != null) {
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "This will un-enroll all students from " + selected.getCourseName() + ". Are you sure?", ButtonType.YES, ButtonType.NO);
                confirmation.setTitle("Confirm Course Removal");
                confirmation.setHeaderText("Remove Course");
                confirmation.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        DataManager.removeCourse(selected);
                        showSuccess("Course removed successfully.");
                    }
                });
            }
        });

        HBox courseManagementBox = new HBox(10, addCourseBtn, updateCourseBtn, removeCourseBtn);

        Button enrollBtn = new Button("Enroll Student");
        enrollBtn.disableProperty().bind(courseComboBox.getSelectionModel().selectedItemProperty().isNull().or(studentComboBox.getSelectionModel().selectedItemProperty().isNull()));
        enrollBtn.setOnAction(_ -> {
            Course course = courseComboBox.getValue();
            Student student = studentComboBox.getValue();
            if (student.getEnrolledCoursesWithGrades().containsKey(course)) {
                showError("Enrollment Failed", "Student is already enrolled in this course.");
            } else {
                student.enrollInCourse(course);
                showSuccess("Enrolled " + student.getFirstName() + " in " + course.getCourseName());
            }
        });

        layout.getChildren().addAll(new Label("Manage Courses:"), courseManagementBox, new Separator(), new Label("Enroll Student in Course:"), new Label("Select Course:"), courseComboBox, new Label("Select Student:"), studentComboBox, enrollBtn);
        return layout;
    }

    @SuppressWarnings("unchecked")
    private VBox createGradeManagementTab() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ComboBox<Student> studentComboBox = new ComboBox<>(DataManager.getStudents());
        studentComboBox.setPromptText("Select a Student");

        TableColumn<Map.Entry<Course, Integer>, String> courseIdCol = new TableColumn<>("Course ID");
        courseIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey().getCourseId()));
        TableColumn<Map.Entry<Course, Integer>, String> courseNameCol = new TableColumn<>("Course Name");
        courseNameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getKey().getCourseName()));
        TableColumn<Map.Entry<Course, Integer>, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(cellData -> {
            int grade = cellData.getValue().getValue();
            return new javafx.beans.property.SimpleStringProperty(grade == -1 ? "Not Graded" : String.valueOf(grade));
        });

        gradeTable.getColumns().setAll(courseIdCol, courseNameCol, gradeCol);
        gradeTable.setPlaceholder(new Label("Select a student to see their courses."));

        studentComboBox.setOnAction(_ -> {
            Student selected = studentComboBox.getValue();
            if (selected != null) {
                gradeTable.setItems(FXCollections.observableArrayList(selected.getEnrolledCoursesWithGrades().entrySet()));
            } else {
                gradeTable.getItems().clear();
            }
        });

        layout.getChildren().addAll(new Label("Select Student:"), studentComboBox, gradeTable, createGradeAssignmentBox(studentComboBox));
        return layout;
    }

    private HBox createGradeAssignmentBox(ComboBox<Student> studentComboBox) {
        HBox assignBox = new HBox(10);
        TextField gradeField = new TextField();
        gradeField.setPromptText("Enter Grade (0-100)");
        Button assignBtn = new Button("Assign / Update Grade");

        assignBtn.disableProperty().bind(gradeTable.getSelectionModel().selectedItemProperty().isNull());
        assignBtn.setOnAction(_ -> {
            Student student = studentComboBox.getValue();
            Map.Entry<Course, Integer> entry = gradeTable.getSelectionModel().getSelectedItem();
            try {
                int grade = Integer.parseInt(gradeField.getText());
                if (grade < 0 || grade > 100) {
                    showError("Input Error", "Grade must be between 0 and 100.");
                    return;
                }
                student.assignGrade(entry.getKey(), grade);
                gradeTable.getItems().setAll(student.getEnrolledCoursesWithGrades().entrySet());
                showSuccess("Grade updated successfully.");
                gradeField.clear();
            } catch (NumberFormatException ex) {
                showError("Input Error", "Please enter a valid number for the grade.");
            }
        });
        assignBox.getChildren().addAll(new Label("New Grade:"), gradeField, assignBtn);
        return assignBox;
    }

    private void handleAddStudent() {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Add New Student");
        dialog.setHeaderText("Enter the new student's details.");
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField studentId = new TextField();
        studentId.setPromptText("Student ID");
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(studentId, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstName, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastName, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                if (studentId.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
                    showError("Input Error", "All fields are required.");
                    return null;
                }
                return new Student(studentId.getText(), firstName.getText(), lastName.getText());
            }
            return null;
        });
        dialog.showAndWait().ifPresent(student -> {
            DataManager.addStudent(student);
            showSuccess("New student added successfully!");
        });
    }

    private void handleUpdateStudent(Student student) {
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Update Student Information");
        dialog.setHeaderText("Editing details for: " + student.getFirstName() + " " + student.getLastName());
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField studentId = new TextField(student.getStudentId());
        studentId.setEditable(false);
        TextField firstName = new TextField(student.getFirstName());
        TextField lastName = new TextField(student.getLastName());

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(studentId, 1, 0);
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(firstName, 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(lastName, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
                    showError("Input Error", "First and Last Name cannot be empty.");
                    return null;
                }
                student.setFirstName(firstName.getText());
                student.setLastName(lastName.getText());
                return student;
            }
            return null;
        });
        dialog.showAndWait().ifPresent(_ -> {
            studentTable.refresh();
            showSuccess("Student information updated successfully!");
        });
    }

    private void handleAddCourse() {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Add New Course");
        dialog.setHeaderText("Enter the new course's details.");
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField courseId = new TextField();
        courseId.setPromptText("Course ID (e.g., CS102)");
        TextField courseName = new TextField();
        courseName.setPromptText("Course Name");

        grid.add(new Label("Course ID:"), 0, 0);
        grid.add(courseId, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1);
        grid.add(courseName, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                if (courseId.getText().isEmpty() || courseName.getText().isEmpty()) {
                    showError("Input Error", "All fields are required.");
                    return null;
                }
                return new Course(courseId.getText(), courseName.getText());
            }
            return null;
        });
        dialog.showAndWait().ifPresent(course -> {
            DataManager.addCourse(course);
            showSuccess("New course '" + course.getCourseName() + "' added successfully!");
        });
    }

    private void handleUpdateCourse(Course course) {
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Update Course Information");
        dialog.setHeaderText("Editing details for: " + course.getCourseName());
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField courseIdField = new TextField(course.getCourseId());
        TextField courseNameField = new TextField(course.getCourseName());

        grid.add(new Label("Course ID:"), 0, 0);
        grid.add(courseIdField, 1, 0);
        grid.add(new Label("Course Name:"), 0, 1);
        grid.add(courseNameField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                if (courseIdField.getText().isEmpty() || courseNameField.getText().isEmpty()) {
                    showError("Input Error", "All fields are required.");
                    return null;
                }
                course.setCourseId(courseIdField.getText());
                course.setCourseName(courseNameField.getText());
                return course;
            }
            return null;
        });
        dialog.showAndWait().ifPresent(_ -> {
            courseComboBox.setItems(null);
            courseComboBox.setItems(DataManager.getCourses());
            showSuccess("Course information updated successfully!");
        });
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}