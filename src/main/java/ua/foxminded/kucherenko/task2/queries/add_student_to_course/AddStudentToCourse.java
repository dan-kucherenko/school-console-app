package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;
import ua.foxminded.kucherenko.task2.queries.StudentCourseExistence;
import ua.foxminded.kucherenko.task2.queries.StudentIdByNameQuery;

import javax.sql.DataSource;

@Component
public class AddStudentToCourse implements IVoidQuery<AddStudentToCourseData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_TO_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/insert_student_course.sql";
    private static final String ADD_TO_COURSE = QueryParser.parseQuery(ADD_TO_COURSE_FILEPATH);

    @Autowired
    public AddStudentToCourse(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void executeQuery(AddStudentToCourseData data) {
        StudentIdByNameQuery studentExistence = new StudentIdByNameQuery(jdbcTemplate.getDataSource());
        Integer studentId = studentExistence.executeQueryWithRes(data.getFirstName(), data.getLastName());
        if (studentId == null) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesn't exist");
        }
        if (data.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course id: it should be more than 0");
        }

        StudentCourseExistence studentCourseExistence = new StudentCourseExistence(jdbcTemplate.getDataSource());
        if (studentCourseExistence.executeQueryWithRes(studentId, data.getCourseId())) {
            throw new IllegalArgumentException("This record already exists");
        }

        int affectedRows = jdbcTemplate.update(ADD_TO_COURSE, studentId, data.getCourseId());

        if (affectedRows > 0) {
            System.out.println("Student with id " + studentId + " was successfully added to course " + data.getCourseId());
        }
    }
}
