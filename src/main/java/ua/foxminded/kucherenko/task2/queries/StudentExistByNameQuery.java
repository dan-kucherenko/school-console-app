package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;

import java.sql.*;

public class StudentExistByNameQuery implements IUtilityQuery<Integer> {
    private final String studentFirstName;
    private final String studentLastName;

    public StudentExistByNameQuery(String studentFirstName, String studentLastName) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
    }

    private static final String STUDENT_EXISTS_QUERY = """
            SELECT student_id FROM school.students
            WHERE first_name = ? AND last_name = ?;
            """;

    @Override
    public Integer executeQueryWithRes() {
        ResultSet resultSet = null;

        int studentId = -1;

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(STUDENT_EXISTS_QUERY)) {
            statement.setString(1, studentFirstName);
            statement.setString(2, studentLastName);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                studentId = resultSet.getInt("student_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentId;
    }
}
