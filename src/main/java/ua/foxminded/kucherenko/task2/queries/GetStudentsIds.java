package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetStudentsIds implements IUtilityQuery<List<Integer>> {
    private final String url;
    private final Properties properties;
    private static final String GET_STUDENTS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_students_id.sql";
    private static final String GET_STUDENTS_ID_QUERY = QueryParser.parseQuery(GET_STUDENTS_ID_FILEPATH);

    public GetStudentsIds(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public List<Integer> executeQueryWithRes() {
        ResultSet resultSet = null;
        List<Integer> studentIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(GET_STUDENTS_ID_QUERY)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                studentIds.add(resultSet.getInt("student_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentIds;
    }
}
