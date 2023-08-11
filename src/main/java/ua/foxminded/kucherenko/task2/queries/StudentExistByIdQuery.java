package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;

public class StudentExistByIdQuery implements IUtilityQuery<Boolean> {
    private final int studentId;

    public StudentExistByIdQuery(int studentId) {
        this.studentId = studentId;
    }

    private static final String STUDENT_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exists.sql";
    private static final String STUDENT_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_EXISTS_QUERY_FILEPATH);

    @Override
    public Boolean executeQueryWithRes() {
        ResultSet resultSet = null;

        boolean studentExists = true;

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(STUDENT_EXISTS_QUERY)) {
            statement.setInt(1, studentId);
            resultSet = statement.executeQuery();
            studentExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentExists;
    }
}
