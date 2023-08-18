package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.*;

public class StudentsGenerator implements IGenerator {
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final int MAX_GROUP_CAPACITY = 30;
    private static final int NO_GROUP_INDEX = 0;
    private static final int GROUP_INDEX = 1;
    private static final int FIRST_NAME_INDEX = 2;
    private static final int LAST_NAME_INDEX = 3;
    private static final String ADD_STUDENT_QUERY_FILEPATH = "src/main/resources/sql_queries/generators/insert_student.sql";
    private static final String ADD_STUDENT_QUERY = QueryParser.parseQuery(ADD_STUDENT_QUERY_FILEPATH);

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
    private final String url;
    private final Properties properties;

    public StudentsGenerator(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void addToDb() {
        Random random = new Random();

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_QUERY)) {

            Set<Integer> assignedStudents = new HashSet<>();
            Map<Integer, Integer> groupCounts = new HashMap<>();

            for (int studentId = 1; studentId <= NUMBER_OF_STUDENTS; studentId++) {
                int groupId = random.nextInt(11);

                while ((groupId != NO_GROUP_INDEX) &&
                        (groupCounts.getOrDefault(groupId, 0) == MAX_GROUP_CAPACITY
                        || assignedStudents.contains(studentId))) {
                    groupId = random.nextInt(11);
                }

                boolean studentShouldBeAddedToGroup = random.nextBoolean();
                if (groupId != NO_GROUP_INDEX && studentShouldBeAddedToGroup) {
                    groupCounts.put(groupId, groupCounts.getOrDefault(groupId, 0) + 1);
                    assignedStudents.add(studentId);
                    statement.setInt(GROUP_INDEX, groupId);
                } else {
                    statement.setNull(GROUP_INDEX, Types.INTEGER);
                }
                String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
                String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

                statement.setString(FIRST_NAME_INDEX, firstName);
                statement.setString(LAST_NAME_INDEX, lastName);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
