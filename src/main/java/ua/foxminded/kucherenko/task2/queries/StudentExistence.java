package ua.foxminded.kucherenko.task2.queries;

import java.sql.*;

public class StudentExistence implements IResultingQuery<Boolean> {
    private final int studentId;

    public StudentExistence(int studentId) {
        this.studentId = studentId;
    }

    private static final String STUDENT_EXISTS_QUERY = """
            SELECT * FROM school.students
            WHERE student_id = ?
            """;

    @Override
    public Boolean executeQueryWithRes() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean studentExists = true;

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(STUDENT_EXISTS_QUERY);
            statement.setInt(1, studentId);
            resultSet = statement.executeQuery();
            studentExists = resultSet.next();
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
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return studentExists;
    }
}
