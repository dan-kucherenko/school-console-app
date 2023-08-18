package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class StudentCourseTable {
    private static final String CREATE_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/generators/create_student_course.sql";
    private static final String CREATE_STUDENT_COURSE_QUERY = QueryParser.parseQuery(CREATE_STUDENT_COURSE_FILEPATH);
    private final String url;
    private final Properties properties;

    public StudentCourseTable(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    public void createStudentCoursesTable() {
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(CREATE_STUDENT_COURSE_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
