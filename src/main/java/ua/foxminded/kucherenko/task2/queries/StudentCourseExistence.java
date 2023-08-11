package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;

public class StudentCourseExistence implements IUtilityQuery<Boolean> {
    private final int studentId;
    private final int courseId;

    public StudentCourseExistence(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    private static final String STUDENT_COURSE_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";
    private static final String STUDENT_COURSE_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_COURSE_EXISTS_QUERY_FILEPATH);

    @Override
    public Boolean executeQueryWithRes() {
        ResultSet resultSet = null;

        boolean studentCourseExists = true;

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
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
