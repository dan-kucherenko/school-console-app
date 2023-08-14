package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

public class GroupsNameGenerator implements IGenerator {
    private static final int GROUPS_NAME_INDEX = 1;
    private static final int GROUPS_QUANTITY = 10;
    private static final String ALPHABET_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String INSERT_GROUP_QUERY_FILEPATH = "src/main/resources/sql_queries/generators/insert_group.sql";
    private static final String INSERT_GROUP_QUERY = QueryParser.parseQuery(INSERT_GROUP_QUERY_FILEPATH);
    private final String url;
    private final Properties properties;

    public GroupsNameGenerator(Configuration configuration){
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }
    @Override
    public void addToDb() {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(INSERT_GROUP_QUERY)) {
            for (int i = 0; i < GROUPS_QUANTITY; i++) {
                String groupName = generateGroupNames();
                statement.setString(GROUPS_NAME_INDEX, groupName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String generateGroupNames() {
        Random random = new Random();
        StringBuilder groupNameBuilder = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            char randomChar = ALPHABET_CHARS.charAt(random.nextInt(ALPHABET_CHARS.length()));
            groupNameBuilder.append(randomChar);
        }
        groupNameBuilder.append("-");
        for (int i = 0; i < 2; i++) {
            char randomDigit = DIGITS.charAt(random.nextInt(DIGITS.length()));
            groupNameBuilder.append(randomDigit);
        }
        return groupNameBuilder.toString();
    }
}