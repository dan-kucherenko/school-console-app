package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GetGroupIds implements IUtilityQuery<List<Integer>> {
    private final String url;
    private final Properties properties;
    private static final String GET_GROUPS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_groups_id.sql";
    private static final String GET_GROUPS_ID_QUERY = QueryParser.parseQuery(GET_GROUPS_ID_FILEPATH);

    public GetGroupIds(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public List<Integer> executeQueryWithRes() {
        ResultSet resultSet = null;
        List<Integer> groupIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(GET_GROUPS_ID_QUERY)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groupIds.add(resultSet.getInt("group_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupIds;
    }
}
