package ua.foxminded.kucherenko.task2.queries.add_student;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import java.sql.*;
import java.util.Properties;

public class AddStudent implements IVoidQuery<AddStudentData> {
    private static final String ADD_STUDENT_QUERY_FILEPATH = "src/main/resources/sql_queries/generators/insert_student.sql";
    private static final String ADD_STUDENT_QUERY = QueryParser.parseQuery(ADD_STUDENT_QUERY_FILEPATH);
    private static final String STUDENT_QUANTITY_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/get_student_group_quantity.sql";
    private static final String STUDENT_QUANTITY_QUERY = QueryParser.parseQuery(STUDENT_QUANTITY_QUERY_FILEPATH);
    private final String url;
    private final Properties properties;

    public AddStudent(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void executeQuery(AddStudentData data) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_QUERY)) {
            if (data.getGroupId() < 0 || groupIsFull(data.getGroupId())) {
                throw new IllegalArgumentException("GroupID should be positive number or group is full");
            }
            if (data.getGroupId() == 0) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, data.getGroupId());
            }
            statement.setString(2, data.getFirstName());
            statement.setString(3, data.getLastName());
            statement.executeUpdate();
            System.out.println("User " + data.getFirstName() + ' ' + data.getLastName() + " from group "
                    + data.getGroupId() + " was successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
