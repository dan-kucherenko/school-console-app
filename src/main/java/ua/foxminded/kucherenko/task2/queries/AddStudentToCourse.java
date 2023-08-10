package ua.foxminded.kucherenko.task2.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddStudentToCourse implements IVoidQuery {
    private int studentId;
    private int courseId;
    private static final String ADD_TO_COURSE = """
            INSERT INTO school.student_courses
            VALUES (?,?);
            """;

    private void passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the studentID: ");
        studentId = sc.nextInt();
        StudentExistQuery studentExistence = new StudentExistQuery(studentId);
        System.out.print("Enter the courseID: ");
        courseId = sc.nextInt();
        if (Boolean.TRUE.equals(studentId < 0 || !studentExistence.executeQueryWithRes() || courseId < 0) || courseId > 10) {
            throw new IllegalArgumentException("Error in input data");
        }
    }

    @Override
    public void executeQuery() {
        passData();
        StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId);
        if (Boolean.TRUE.equals(studentCourseExistence.executeQueryWithRes())) {
            throw new IllegalArgumentException("This record already exists");
        }

        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(ADD_TO_COURSE)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
            System.out.println("Student with id " + studentId + " was successfully added to course " + courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
