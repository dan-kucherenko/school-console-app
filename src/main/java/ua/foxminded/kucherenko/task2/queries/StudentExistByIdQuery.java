package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.Properties;

public class StudentExistByIdQuery implements IUtilityQuery<Boolean> {
    private final int studentId;
    private final String url;
    private final Properties properties;

    public StudentExistByIdQuery(int studentId, Configuration configuration) {
        this.studentId = studentId;
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    private static final String STUDENT_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exists.sql";
    private static final String STUDENT_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_EXISTS_QUERY_FILEPATH);

    @Override
    public Boolean executeQueryWithRes() {
        ResultSet resultSet = null;

        boolean studentExists = true;

        try (Connection connection = DriverManager.getConnection(url, properties);
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
