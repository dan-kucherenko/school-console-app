package ua.foxminded.kucherenko.task2.generators;

import java.sql.*;
import java.util.*;

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

            Set<Integer> assignedStudents = new HashSet<>();
            Map<Integer, Integer> groupCounts = new HashMap<>();

            for (int studentId = 1; studentId <= NUMBER_OF_STUDENTS; studentId++) {
                int groupId = random.nextInt(11);

                while (groupId != 0 && groupCounts.getOrDefault(groupId, 0) >= 30
                        || assignedStudents.contains(studentId)) {
                    groupId = random.nextInt(11);
                }

                if (groupId != 0 && random.nextBoolean()) {
                    groupCounts.put(groupId, groupCounts.getOrDefault(groupId, 0) + 1);
                    assignedStudents.add(studentId);
                    statement.setInt(1, groupId);
                } else {
                    statement.setNull(1, Types.INTEGER);
                }
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

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
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
