package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
    private static final String GET_COURSES_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_courses_id.sql";
    private static final String GET_COURSES_NUM_FILEPATH = "src/main/resources/sql_queries/dao/course/get_courses_num.sql";

    private static final String GET_COURSES_ID_QUERY = QueryParser.parseQuery(GET_COURSES_ID_FILEPATH);
    private static final String GET_COURSES_NUM_QUERY = QueryParser.parseQuery(GET_COURSES_NUM_FILEPATH);

    @Override
    public Optional<Course> get(int id) {
        try {
            return Optional.ofNullable(em.find(Course.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getAll() {
        return em.createQuery("FROM Course", Course.class)
                .getResultList();
    }

    @Override
    public Integer countAll() {
        Long count = em.createQuery(GET_COURSES_NUM_QUERY, Long.class)
                .getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    public List<Integer> getAllCourseIds() {
        return em.createQuery(GET_COURSES_ID_QUERY, Integer.class)
                .getResultList();
    }

    @Override
    public void save(Course course) {
        em.persist(course);
    }

    @Override
    public void update(int id, Course course) {
        Course existingCourse = em.find(Course.class, id);
        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setCourseDescription(course.getCourseDescription());
    }

    @Override
    public void delete(int id) {
        Course existingCourse = em.find(Course.class, id);
        em.remove(existingCourse);
    }
}
