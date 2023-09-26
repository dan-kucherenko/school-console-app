package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.List;

@Transactional
@Repository
public class StudentCourseDao {
    @PersistenceContext
    private EntityManager em;
    private static final String GET_ALL_STUDENT_COURSES_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/get_all_student_courses.sql";
    private static final String GET_STUDENT_COURSES_NUM_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/get_student_courses_num.sql";
    private static final String DELETE_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/delete_student_courses.sql";
    private static final String STUDENT_COURSE_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";

    private static final String GET_ALL_STUDENT_COURSES = QueryParser.parseQuery(GET_ALL_STUDENT_COURSES_FILEPATH);
    private static final String GET_STUDENT_COURSES_NUM = QueryParser.parseQuery(GET_STUDENT_COURSES_NUM_FILEPATH);
    private static final String DELETE_STUDENT_COURSE = QueryParser.parseQuery(DELETE_STUDENT_COURSE_FILEPATH);
    private static final String STUDENT_COURSE_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_COURSE_EXISTS_QUERY_FILEPATH);

    public List<StudentCourse> getAll() {
        return em.createQuery(GET_ALL_STUDENT_COURSES, StudentCourse.class).getResultList();
    }

    public Integer countAll() {
        Long count = em.createQuery(GET_STUDENT_COURSES_NUM, Long.class).getSingleResult();
        return count.intValue();
    }

    public boolean exists(int studentId, int courseId) {
        Long count = em.createQuery(STUDENT_COURSE_EXISTS_QUERY, Long.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getSingleResult();
        return count != null && count > 0;
    }

    public void save(StudentCourse studentCourse) {
        em.persist(studentCourse);
    }

    public void delete(int studentId, int courseId) {
        em.createQuery(DELETE_STUDENT_COURSE)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .executeUpdate();
    }
}
