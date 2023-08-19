package ua.foxminded.kucherenko.task2.db;

import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class CreateTestDatabase {
    private static final String CREATE_DB_FILEPATH = "src/test/resources/database/create_db.sql";
    private final String url;
    private final Properties properties;

    public CreateTestDatabase(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    public void initDatabase() {
        final String createDatabaseQuery = QueryParser.parseQuery(CREATE_DB_FILEPATH);
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(createDatabaseQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
