package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveFromCourse implements IVoidQuery<RemoveFromCourseData> {
    private static final String REMOVE_STUDENT_FROM_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/remove_from_course.sql";
    private static final String REMOVE_STUDENT_FROM_COURSE = QueryParser.parseQuery(REMOVE_STUDENT_FROM_COURSE_FILEPATH);

    @Override
    public void executeQuery(RemoveFromCourseData data) {
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE)) {
            statement.setInt(1, data.getStudentId());
            statement.setInt(2, data.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
