package ua.foxminded.kucherenko.task2.queries.delete_student;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DeleteStudent implements IVoidQuery<DeleteStudentData> {
    private static final String DELETE_STUDENT_BY_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/delete_by_student_id.sql";
    private static final String DELETE_STUDENT_BY_ID = QueryParser.parseQuery(DELETE_STUDENT_BY_ID_FILEPATH);
    private final String url;
    private final Properties properties;

    public DeleteStudent(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void executeQuery(DeleteStudentData data) {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_BY_ID)) {
            statement.setInt(1, data.getStudentId());
            statement.executeUpdate();
            System.out.println("Student with id " + data.getStudentId() + " was successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
