package ua.foxminded.kucherenko.task2.queries.find_stud_by_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import javax.sql.DataSource;
import java.util.List;

@Component
public class FindStudentByCourse implements IResultingQuery<List<Student>, FindStudentByCourseData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_STUDENT_BY_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/find_student_by_course.sql";
    private static final String FIND_STUDENT_BY_COURSE = QueryParser.parseQuery(FIND_STUDENT_BY_COURSE_FILEPATH);

    @Autowired
    public FindStudentByCourse(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Student> executeQueryWithRes(FindStudentByCourseData data) {
        if (data.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name can't be blank");
        }

        return jdbcTemplate.query(
                FIND_STUDENT_BY_COURSE,
                new Object[]{data.getCourseName()},
                new BeanPropertyRowMapper<>(Student.class)
        );
    }
}
