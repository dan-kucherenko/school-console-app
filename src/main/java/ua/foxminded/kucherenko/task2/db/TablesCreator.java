package ua.foxminded.kucherenko.task2.db;

import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TablesCreator {
    private static final String CREATE_TABLES_FILEPATH = "src/main/resources/sql_queries/database/create_tables.sql";
    private final String url;
    private final Properties properties;

    public TablesCreator(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    public void createTables() {
        final String tableCreationQuery = QueryParser.parseQuery(CREATE_TABLES_FILEPATH);
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(tableCreationQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
