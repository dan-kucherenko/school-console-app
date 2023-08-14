package ua.foxminded.kucherenko.task2.queries.find_stud_by_course;

import ua.foxminded.kucherenko.task2.db.Configuration;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FindStudentByCourse implements IResultingQuery<List<Student>, FindStudentByCourseData> {
    private static final String FIND_STUDENT_BY_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/find_student_by_course.sql";
    private static final String FIND_STUDENT_BY_COURSE = QueryParser.parseQuery(FIND_STUDENT_BY_COURSE_FILEPATH);
    private final String url;
    private final Properties properties;

    public FindStudentByCourse(Configuration configuration) {
        this.url = configuration.getUrl();
        this.properties = configuration.getProps();
    }

    @Override
    public List<Student> executeQueryWithRes(FindStudentByCourseData data) {
        List<Student> res = null;
        ResultSet resultSet = null;
        if (data.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name cant be null");
        }
        try (Connection connection = DriverManager.getConnection(url, properties);
             PreparedStatement statement = connection.prepareStatement(FIND_STUDENT_BY_COURSE)) {
            statement.setString(1, data.getCourseName());
            resultSet = statement.executeQuery();
            res = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Student> parseResultSet(ResultSet resultSet) {
        List<Student> res = new ArrayList<>();
        Student student = null;
        try {
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                if (groupId == 0) {
                    student = new Student(studentId, firstName, lastName);
                } else {
                    student = new Student(studentId, groupId, firstName, lastName);
                }
                res.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
