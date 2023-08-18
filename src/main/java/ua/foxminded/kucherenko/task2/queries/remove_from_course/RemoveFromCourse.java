package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class RemoveFromCourse implements IVoidQuery<RemoveFromCourseData> {
    private static final String REMOVE_STUDENT_FROM_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/remove_from_course.sql";
    private static final String REMOVE_STUDENT_FROM_COURSE = QueryParser.parseQuery(REMOVE_STUDENT_FROM_COURSE_FILEPATH);
    private final String url;
    private final Properties properties;

    public RemoveFromCourse(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void executeQuery(RemoveFromCourseData data) {
        if (data.getStudentId() == null) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }
        if (data.getCourseId() <= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE)) {
            statement.setInt(1, data.getStudentId());
            statement.setInt(2, data.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}