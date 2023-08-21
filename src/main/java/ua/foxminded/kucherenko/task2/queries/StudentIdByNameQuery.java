package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.Properties;

public class StudentIdByNameQuery implements IUtilityQuery<Integer> {
    private final String studentFirstName;
    private final String studentLastName;
    private final String url;
    private final Properties properties;
    private static final String STUDENT_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exist_by_name.sql";
    private static final String STUDENT_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_EXISTS_QUERY_FILEPATH);

    public StudentIdByNameQuery(String studentFirstName, String studentLastName, Configuration configuration) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public Integer executeQueryWithRes() {
        ResultSet resultSet = null;

        Integer studentId = null;

        try (Connection connection = DriverManager.getConnection(url, properties);
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
