package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseCreator {
    private static final String CREATE_DB_FILEPATH = "src/main/resources/sql_queries/database/create_db.sql";
    private static final Dotenv DOTENV_READER = Dotenv.load();
    private static final String BASIC_URL = DOTENV_READER.get("BASIC_URL");
    private static final String USER_ADMIN = DOTENV_READER.get("USER_ADMIN");
    private static final String PASSWORD_ADMIN = DOTENV_READER.get("PASSWORD_ADMIN");
    private static final Properties PROPERTIES = getDefaultProperties();

    public DatabaseCreator() {
    }

    private static Properties getDefaultProperties() {
        Properties props = new Properties();
        props.setProperty("user", USER_ADMIN);
        props.setProperty("password", PASSWORD_ADMIN);
        return props;
    }

    public void initDatabase() {
        final String createDatabaseQuery = QueryParser.parseQuery(CREATE_DB_FILEPATH);
        try (Connection connection = DriverManager.getConnection(BASIC_URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(createDatabaseQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
