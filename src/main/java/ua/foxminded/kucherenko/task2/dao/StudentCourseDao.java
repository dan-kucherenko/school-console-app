package ua.foxminded.kucherenko.task2.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;

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
        try {
            return List.of(jdbcTemplate.queryForObject(GET_ALL_STUDENT_COURSES, StudentCourse.class));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean exists(Integer studentId, int courseId) {
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

    public void save(StudentCourse studentCourse) {
        jdbcTemplate.update(ADD_STUDENT_COURSE, studentCourse.getStudentId(), studentCourse.getCourseId());
    }

    public void delete(int studentId, int courseId) {
        jdbcTemplate.update(DELETE_STUDENT_COURSE, studentId, courseId);
    }
}
