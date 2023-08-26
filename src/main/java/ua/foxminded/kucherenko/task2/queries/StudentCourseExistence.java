package ua.foxminded.kucherenko.task2.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import javax.sql.DataSource;

@Component
public class StudentCourseExistence {
    private final JdbcTemplate jdbcTemplate;
    private static final String STUDENT_COURSE_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";
    private static final String STUDENT_COURSE_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_COURSE_EXISTS_QUERY_FILEPATH);

    @Autowired
    public StudentCourseExistence(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Boolean executeQueryWithRes(Integer studentId, Integer courseId) {
        if (studentId == null) {
            return false;
        }

        try {
            Integer count = jdbcTemplate.queryForObject(
                    STUDENT_COURSE_EXISTS_QUERY,
                    new Object[]{studentId, courseId},
                    Integer.class
            );
            return count != null && count > 0;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
