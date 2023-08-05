package ua.foxminded.kucherenko.task2.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RemoveFromCourse implements IVoidQuery {
    private static final String REMOVE_STUDENT_FROM_COURSE = """
            DELETE FROM school.student_courses
            WHERE student_id = ? AND course_id = ?
            """;

    @Override
    public void executeOwnQuery() {
        Connection connection = null;
        PreparedStatement statement = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the studentId to delete: ");
        int studentId = sc.nextInt();
        StudentExistence studentExistence = new StudentExistence(studentId);
        System.out.println("Enter the courseId to delete: ");
        int courseId = sc.nextInt();
        if (Boolean.TRUE.equals(studentId < 0 || !studentExistence.executeQueryWithRes() && courseId < 0) || courseId > 10) {
            throw new IllegalArgumentException("Error in input data");
        }

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE);
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
