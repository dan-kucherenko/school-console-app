package ua.foxminded.kucherenko.task2.queries;

import java.sql.*;
import java.util.Scanner;

public class AddStudent implements IVoidQuery {
    private int groupId;
    private String firstName;
    private String lastName;
    private static final String ADD_STUDENT_QUERY = """
            INSERT INTO school.students (group_id, first_name, last_name)
            VALUES (?, ?, ?);
            """;

    private void passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student groupID (0 if student isnt in group): ");
        groupId = sc.nextInt();
        if (groupId < 0 || groupId > 10 || groupIsFull(groupId)) {
            throw new IllegalArgumentException("Error in input data");
        }
        System.out.print("Enter the student first name: ");
        firstName = sc.next();

        System.out.print("Enter the student last name: ");
        lastName = sc.next();
    }

    @Override
    public void executeOwnQuery() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(ADD_STUDENT_QUERY);
            passData();
            if (groupId == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, groupId);
            }
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.executeUpdate();
            System.out.println("User " + firstName + ' ' + lastName + " from group " + groupId + " was successfully added");
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

    private boolean groupIsFull(int groupId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int groupQuantity = 0;
        final String studentQuantityQuery = """
                SELECT COUNT (*) AS num_of_students FROM school.students
                WHERE group_id = ?;
                """;
        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(studentQuantityQuery);

            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            resultSet.next();
            groupQuantity = resultSet.getInt("num_of_students");
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
        return groupQuantity >= 30;
    }
}
