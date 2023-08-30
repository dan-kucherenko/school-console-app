package ua.foxminded.kucherenko.task2.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Course;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDao implements Dao<Course> {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_COURSE_BY_ID_FILEPATH = "src/main/resources/sql_queries/dao/course/get_course.sql";
    private static final String GET_ALL_COURSES_FILEPATH = "src/main/resources/sql_queries/dao/course/get_all_courses.sql";
    private static final String ADD_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/course/add_course.sql";
    private static final String UPDATE_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/course/update_course.sql";
    private static final String DELETE_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/course/delete_course.sql";

    private static final String GET_COURSE_BY_ID = QueryParser.parseQuery(GET_COURSE_BY_ID_FILEPATH);
    private static final String GET_ALL_COURSES = QueryParser.parseQuery(GET_ALL_COURSES_FILEPATH);
    private static final String ADD_COURSE = QueryParser.parseQuery(ADD_COURSE_FILEPATH);
    private static final String UPDATE_COURSE = QueryParser.parseQuery(UPDATE_COURSE_FILEPATH);
    private static final String DELETE_COURSE = QueryParser.parseQuery(DELETE_COURSE_FILEPATH);

    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> get(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GET_COURSE_BY_ID, new Object[]{id}, Course.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {
        try {
            return List.of(jdbcTemplate.queryForObject(GET_ALL_COURSES, Course.class));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Course course) {
        jdbcTemplate.update(ADD_COURSE, course.getCourseName(), course.getCourseDescription());
    }

    @Override
    public void update(int id, Course course) {
        jdbcTemplate.update(UPDATE_COURSE, course.getCourseName(), course.getCourseDescription(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_COURSE, id);
    }
}
