package ua.foxminded.kucherenko.task2.queries.add_student;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import java.sql.*;

public class AddStudent implements IVoidQuery<AddStudentData> {
    private static final String ADD_STUDENT_QUERY_FILEPATH = "src/main/resources/sql_queries/generators/insert_student.sql";
    private static final String ADD_STUDENT_QUERY = QueryParser.parseQuery(ADD_STUDENT_QUERY_FILEPATH);

    @Override
    public void executeQuery(AddStudentData data) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_QUERY)) {
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
}
