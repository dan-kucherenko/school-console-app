package ua.foxminded.kucherenko.task2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentDao implements Dao<Student> {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_STUDENT_BY_ID_FILEPATH = "src/main/resources/sql_queries/dao/student/get_student.sql";
    private static final String GET_ALL_STUDENTS_FILEPATH = "src/main/resources/sql_queries/dao/student/get_all_students.sql";
    private static final String GET_STUDENTS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_students_id.sql";
    private static final String GET_STUDENTS_NUM_FILEPATH = "src/main/resources/sql_queries/dao/student/get_students_num.sql";
    private static final String ADD_STUDENT_FILEPATH = "src/main/resources/sql_queries/dao/student/add_student.sql";
    private static final String UPDATE_STUDENT_FILEPATH = "src/main/resources/sql_queries/dao/student/update_student.sql";
    private static final String DELETE_STUDENT_FILEPATH = "src/main/resources/sql_queries/dao/student/delete_student.sql";
    private static final String FIND_STUDENT_BY_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/find_student_by_course.sql";
    private static final String STUDENT_ID_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exist_by_name.sql";

    private static final String GET_STUDENT_BY_ID = QueryParser.parseQuery(GET_STUDENT_BY_ID_FILEPATH);
    private static final String GET_ALL_STUDENTS = QueryParser.parseQuery(GET_ALL_STUDENTS_FILEPATH);
    private static final String GET_STUDENTS_ID_QUERY = QueryParser.parseQuery(GET_STUDENTS_ID_FILEPATH);
    private static final String GET_STUDENTS_NUM_QUERY = QueryParser.parseQuery(GET_STUDENTS_NUM_FILEPATH);
    private static final String ADD_STUDENT = QueryParser.parseQuery(ADD_STUDENT_FILEPATH);
    private static final String UPDATE_STUDENT = QueryParser.parseQuery(UPDATE_STUDENT_FILEPATH);
    private static final String DELETE_STUDENT = QueryParser.parseQuery(DELETE_STUDENT_FILEPATH);
    private static final String FIND_STUDENT_BY_COURSE = QueryParser.parseQuery(FIND_STUDENT_BY_COURSE_FILEPATH);
    private static final String STUDENT_ID_QUERY = QueryParser.parseQuery(STUDENT_ID_QUERY_FILEPATH);


    @Autowired
    public StudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Student> get(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GET_STUDENT_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Student> getByCourse(String courseName) {
        try {
            return jdbcTemplate.query(
                    FIND_STUDENT_BY_COURSE,
                    new Object[]{courseName},
                    new BeanPropertyRowMapper<>(Student.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id;
        try {
            id = jdbcTemplate.queryForObject(
                    STUDENT_ID_QUERY,
                    new Object[]{firstName, lastName},
                    Integer.class
            );
            return id;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(GET_ALL_STUDENTS, new BeanPropertyRowMapper<>(Student.class));
    }

    @Override
    public Integer countAll() {
        return jdbcTemplate.queryForObject(GET_STUDENTS_NUM_QUERY, Integer.class);
    }

    public List<Integer> getAllStudentIds() {
        return jdbcTemplate.queryForList(GET_STUDENTS_ID_QUERY, Integer.class);
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update(ADD_STUDENT, student.getGroupId(), student.getFirstName(), student.getLastName());
    }

    @Override
    public void update(int id, Student student) {
        jdbcTemplate.update(UPDATE_STUDENT, student.getGroupId(), student.getFirstName(), student.getLastName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_STUDENT, id);
    }
}
