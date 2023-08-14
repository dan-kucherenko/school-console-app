package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.Properties;

public class StudentCourseExistence implements IUtilityQuery<Boolean> {
    private final int studentId;
    private final int courseId;
    private final String url;
    private final Properties properties;

    public StudentCourseExistence(int studentId, int courseId, Configuration configuration) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    private static final String STUDENT_COURSE_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";
    private static final String STUDENT_COURSE_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_COURSE_EXISTS_QUERY_FILEPATH);

    @Override
    public Boolean executeQueryWithRes() {
        ResultSet resultSet = null;

        boolean studentCourseExists = true;

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(STUDENT_COURSE_EXISTS_QUERY)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            resultSet = statement.executeQuery();
            studentCourseExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCourseExists;
    }
}
