package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;
import ua.foxminded.kucherenko.task2.queries.StudentIdByNameQuery;

import javax.sql.DataSource;

@Component
public class RemoveFromCourse implements IVoidQuery<RemoveFromCourseData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String REMOVE_STUDENT_FROM_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/remove_from_course.sql";
    private static final String REMOVE_STUDENT_FROM_COURSE = QueryParser.parseQuery(REMOVE_STUDENT_FROM_COURSE_FILEPATH);

    @Autowired
    RemoveFromCourse(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void executeQuery(RemoveFromCourseData data) {
        StudentIdByNameQuery studentExistence = new StudentIdByNameQuery(jdbcTemplate.getDataSource());
        Integer studentId = studentExistence.executeQueryWithRes(data.getFirstName(), data.getLastName());

        if (studentId == null) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }

        if (data.getCourseId() <= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }

        jdbcTemplate.update(REMOVE_STUDENT_FROM_COURSE, studentId, data.getCourseId());
    }
}
