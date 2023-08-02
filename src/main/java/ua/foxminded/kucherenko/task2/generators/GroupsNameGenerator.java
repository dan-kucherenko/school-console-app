package ua.foxminded.kucherenko.task2.generators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class GroupsNameGenerator implements IGenerator {
    private static final int GROUPS_QUANTITY = 10;
    private static final String QUERY = "INSERT INTO school.groups (group_name) VALUES (?)";

    @Override
    public void addToDb() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(QUERY);
            for (int i = 0; i < GROUPS_QUANTITY; i++) {
                String groupName = generateGroupNames();
                statement.setString(1, groupName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateGroupNames() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        Random random = new Random();
        StringBuilder groupNameBuilder = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            groupNameBuilder.append(randomChar);
        }
        groupNameBuilder.append("-");
        for (int i = 0; i < 2; i++) {
            char randomDigit = numbers.charAt(random.nextInt(numbers.length()));
            groupNameBuilder.append(randomDigit);
        }
        return groupNameBuilder.toString();
    }
}