package ua.foxminded.kucherenko.task2.queries;

import java.sql.*;

public class StudentCourseExistence implements IResultingQuery<Boolean> {
    private final int studentId;
    private final int courseId;

    public StudentCourseExistence(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    private static final String STUDENT_EXISTS_QUERY = """
            SELECT * FROM school.student_courses
            WHERE student_id = ? AND course_id = ?;
            """;

    @Override
    public Boolean executeQueryWithRes() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        boolean studentCourseExists = true;

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(STUDENT_EXISTS_QUERY);

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            resultSet = statement.executeQuery();
            studentCourseExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return studentCourseExists;
    }
}
