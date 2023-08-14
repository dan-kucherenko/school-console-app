package ua.foxminded.kucherenko.task2.queries.add_student;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IInputParser;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class AddStudentInput implements IInputParser<AddStudentData> {
    private static final String STUDENT_QUANTITY_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/get_student_group_quantity.sql";
    private static final String STUDENT_QUANTITY_QUERY = QueryParser.parseQuery(STUDENT_QUANTITY_QUERY_FILEPATH);
    private final String url;
    private final Properties properties;

    public AddStudentInput(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    public AddStudentData passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the student groupID (0 if student isnt in group): ");
        int groupId = sc.nextInt();
        if (groupId <= 0 || groupId > 10 || groupIsFull(groupId)) {
            throw new IllegalArgumentException("GroupID should be between 1 and 10 or group is full");
        }
        System.out.print("Enter the student first name: ");
        String firstName = sc.next();

        System.out.print("Enter the student last name: ");
        String lastName = sc.next();

        return new AddStudentData(groupId, firstName, lastName);
    }

    private boolean groupIsFull(int groupId) {
        ResultSet resultSet = null;
        int groupQuantity = 0;
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(STUDENT_QUANTITY_QUERY)) {

            statement.setInt(1, groupId);
            resultSet = statement.executeQuery();
            resultSet.next();
            groupQuantity = resultSet.getInt("num_of_students");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupQuantity >= 30;
    }
}
