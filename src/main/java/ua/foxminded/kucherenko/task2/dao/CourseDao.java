package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Course;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class CourseDao implements Dao<Course> {
    @PersistenceContext
    private EntityManager em;
    private static final String GET_COURSE_BY_ID_FILEPATH = "src/main/resources/sql_queries/dao/course/get_course.sql";
    private static final String GET_ALL_COURSES_FILEPATH = "src/main/resources/sql_queries/dao/course/get_all_courses.sql";
    private static final String GET_COURSES_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_courses_id.sql";
    private static final String GET_COURSES_NUM_FILEPATH = "src/main/resources/sql_queries/dao/course/get_courses_num.sql";
    private static final String UPDATE_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/course/update_course.sql";
    private static final String DELETE_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/course/delete_course.sql";

    private static final String GET_COURSE_BY_ID = QueryParser.parseQuery(GET_COURSE_BY_ID_FILEPATH);
    private static final String GET_ALL_COURSES = QueryParser.parseQuery(GET_ALL_COURSES_FILEPATH);
    private static final String GET_COURSES_ID_QUERY = QueryParser.parseQuery(GET_COURSES_ID_FILEPATH);
    private static final String GET_COURSES_NUM_QUERY = QueryParser.parseQuery(GET_COURSES_NUM_FILEPATH);
    private static final String UPDATE_COURSE = QueryParser.parseQuery(UPDATE_COURSE_FILEPATH);
    private static final String DELETE_COURSE = QueryParser.parseQuery(DELETE_COURSE_FILEPATH);

    @Override
    public Optional<Course> get(int id) {
        try {
            return Optional.ofNullable(em.createQuery(GET_COURSE_BY_ID, Course.class)
                    .setParameter("courseId", id)
                    .getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {
        return em.createQuery(GET_ALL_COURSES, Course.class).getResultList();
    }

    @Override
    public Integer countAll() {
        Long count = em.createQuery(GET_COURSES_NUM_QUERY, Long.class)
                .getSingleResult();
        return count.intValue();
    }

    public List<Integer> getAllCourseIds() {
        return em.createQuery(GET_COURSES_ID_QUERY, Integer.class).getResultList();
    }

    @Override
    public void save(Course course) {
        em.persist(course);
    }

    @Override
    public void update(int id, Course course) {
        em.createQuery(UPDATE_COURSE)
                .setParameter("courseName", course.getCourseName())
                .setParameter("courseDescription", course.getCourseDescription())
                .setParameter("courseId", id)
                .executeUpdate();
    }

    @Override
    public void delete(int id) {
        em.createQuery(DELETE_COURSE)
                .setParameter("courseId", id)
                .executeUpdate();
    }
}
