package ua.foxminded.kucherenko.task2.queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DeleteStudent implements IVoidQuery {
    private int studentId;
    private static final String DELETE_STUDENT_BY_ID = """
            DELETE FROM school.students
            WHERE student_id = ?;
            """;

    private void passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the studentID to delete: ");
        studentId = sc.nextInt();
        StudentExistence studentExistence = new StudentExistence(studentId);
        if (studentId < 0 || Boolean.TRUE.equals(!studentExistence.executeQueryWithRes())) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }
    }

    @Override
    public void executeOwnQuery() {
        Connection connection = null;
        PreparedStatement statement = null;

        passData();
        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(DELETE_STUDENT_BY_ID);

            statement.setInt(1, studentId);
            statement.executeUpdate();
            System.out.println("Student with id " + studentId + " was successfully deleted");
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
