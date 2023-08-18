package ua.foxminded.kucherenko.task2.db;

import io.github.cdimascio.dotenv.Dotenv;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TablesCreator {
    private static final String CREATE_TABLES_FILEPATH = "src/main/resources/sql_queries/database/create_tables.sql";
    private static final Dotenv DOTENV_READER = Dotenv.load();
    private static final String DATABASE_URL = DOTENV_READER.get("DATABASE_URL");
    private static final String SCHOOL_USER = DOTENV_READER.get("SCHOOL_USER");
    private static final String SCHOOL_PASSWORD = DOTENV_READER.get("SCHOOL_PASSWORD");
    private static final Properties PROPERTIES = getDefaultProperties();


    private static Properties getDefaultProperties() {
        Properties props = new Properties();
        props.setProperty("user", SCHOOL_USER);
        props.setProperty("password", SCHOOL_PASSWORD);
        return props;
    }

    public void createTables() {
        final String tableCreationQuery = QueryParser.parseQuery(CREATE_TABLES_FILEPATH);
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(tableCreationQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
