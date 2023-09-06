package ua.foxminded.kucherenko.task2.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.List;

@Repository
public class StudentCourseDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL_STUDENT_COURSES_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/get_all_student_courses.sql";
    private static final String ADD_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/add_student_courses.sql";
    private static final String DELETE_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/delete_student_courses.sql";
    private static final String STUDENT_COURSE_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";

    private static final String GET_ALL_STUDENT_COURSES = QueryParser.parseQuery(GET_ALL_STUDENT_COURSES_FILEPATH);
    private static final String ADD_STUDENT_COURSE = QueryParser.parseQuery(ADD_STUDENT_COURSE_FILEPATH);
    private static final String DELETE_STUDENT_COURSE = QueryParser.parseQuery(DELETE_STUDENT_COURSE_FILEPATH);
    private static final String STUDENT_COURSE_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_COURSE_EXISTS_QUERY_FILEPATH);

    public StudentCourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StudentCourse> getAll() {
        return jdbcTemplate.query(GET_ALL_STUDENT_COURSES, new BeanPropertyRowMapper<>(StudentCourse.class));
    }

    public boolean exists(int studentId, int courseId) {
        Integer count = jdbcTemplate.queryForObject(
                STUDENT_COURSE_EXISTS_QUERY,
                new Object[]{studentId, courseId},
                Integer.class
        );
        return count != null && count > 0;
    }

    public void save(StudentCourse studentCourse) {
        jdbcTemplate.update(ADD_STUDENT_COURSE, studentCourse.getStudentId(), studentCourse.getCourseId());
    }

    public void delete(int studentId, int courseId) {
        jdbcTemplate.update(DELETE_STUDENT_COURSE, studentId, courseId);
    }
}
