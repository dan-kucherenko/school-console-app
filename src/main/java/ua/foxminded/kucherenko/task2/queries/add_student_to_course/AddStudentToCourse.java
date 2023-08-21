package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;
import ua.foxminded.kucherenko.task2.queries.StudentCourseExistence;
import ua.foxminded.kucherenko.task2.queries.StudentIdByNameQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class AddStudentToCourse implements IVoidQuery<AddStudentToCourseData> {
    private static final String ADD_TO_COURSE_FILEPATH = "src/main/resources/sql_queries/generators/insert_student_course.sql";
    private static final String ADD_TO_COURSE = QueryParser.parseQuery(ADD_TO_COURSE_FILEPATH);
    private final String url;
    private final Properties properties;
    private final Configuration configuration;

    public AddStudentToCourse(Configuration configuration) {
        this.configuration = configuration;
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public void executeQuery(AddStudentToCourseData data) {
        StudentIdByNameQuery studentExistence = new StudentIdByNameQuery(data.getFirstName(), data.getLastName(), configuration);
        Integer studentId = studentExistence.executeQueryWithRes();
        if (studentId == null) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesnt exist");
        }
        if (data.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course id: it should be more than 0");
        }
        StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, data.getCourseId(), configuration);
        if (studentCourseExistence.executeQueryWithRes()) {
            throw new IllegalArgumentException("This record already exists");
        }

        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(ADD_TO_COURSE)) {
            statement.setInt(1, studentId);
            statement.setInt(2, data.getCourseId());
            statement.executeUpdate();
            System.out.println("Student with id " + studentId + " was successfully added to course " + data.getCourseId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
