package ua.foxminded.kucherenko.task2.queries;

import java.sql.*;

public class StudentExistQuery implements IResultingQuery<Boolean> {
    private final int studentId;

    public StudentExistQuery(int studentId) {
        this.studentId = studentId;
    }

    private static final String STUDENT_EXISTS_QUERY = """
            SELECT * FROM school.students
            WHERE student_id = ?
            """;

    @Override
    public Boolean executeQueryWithRes() {
        ResultSet resultSet = null;

        boolean studentExists = true;

        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(STUDENT_EXISTS_QUERY)) {
            statement.setInt(1, studentId);
            resultSet = statement.executeQuery();
            studentExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentExists;
    }

    @Override
    public Boolean parseResultSet(ResultSet resultSet) {
        // not implemented because it's useless here
        return true;
    }
}
