package ua.foxminded.kucherenko.task2.generators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class StudentsGenerator implements IGenerator {
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final String QUERY = "INSERT INTO school.students (group_id, first_name, last_name) VALUES (?, ?, ?)";

    private static final String[] FIRST_NAMES = {
            "John", "Emma", "Michael", "Sophia", "William",
            "Olivia", "James", "Ava", "Alexander", "Isabella",
            "Daniel", "Mia", "Joseph", "Amelia", "David",
            "Charlotte", "Andrew", "Ella", "Anthony", "Emily"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Brown", "Lee", "Davis",
            "Garcia", "Martinez", "Miller", "Wilson", "Anderson",
            "Taylor", "Thomas", "Jackson", "White", "Harris",
            "Martin", "Lewis", "Allen", "Young", "Clark"
    };

    @Override
    public void addToDb() {
        Connection connection = null;
        PreparedStatement statement = null;
        Random random = new Random();

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(QUERY);

            for (int i = 0; i < NUMBER_OF_STUDENTS; i++) {
                int groupId = random.nextInt(10) + 1;
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

                statement.setInt(1, groupId);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
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
}
