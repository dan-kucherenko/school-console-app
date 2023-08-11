package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;
import ua.foxminded.kucherenko.task2.queries.StudentCourseExistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStudentToCourse implements IVoidQuery<AddStudentToCourseData> {
    private static final String ADD_TO_COURSE_FILEPATH = "src/main/resources/sql_queries/generators/insert_student_course.sql";
    private static final String ADD_TO_COURSE = QueryParser.parseQuery(ADD_TO_COURSE_FILEPATH);

    @Override
    public void executeQuery(AddStudentToCourseData data) {
        StudentCourseExistence studentCourseExistence = new StudentCourseExistence(data.getStudentId(), data.getCourseId());
        if (Boolean.TRUE.equals(studentCourseExistence.executeQueryWithRes())) {
            throw new IllegalArgumentException("This record already exists");
        }

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(ADD_TO_COURSE)) {
            statement.setInt(1, data.getStudentId());
            statement.setInt(2, data.getCourseId());
            statement.executeUpdate();
            System.out.println("Student with id " + data.getStudentId() + " was successfully added to course " + data.getCourseId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
