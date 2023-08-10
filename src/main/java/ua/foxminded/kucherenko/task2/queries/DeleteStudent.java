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
        StudentExistQuery studentExistence = new StudentExistQuery(studentId);
        if (studentId < 0 || Boolean.TRUE.equals(!studentExistence.executeQueryWithRes())) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }
    }

    @Override
    public void executeQuery() {
        passData();
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
            System.out.println("Student with id " + studentId + " was successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
