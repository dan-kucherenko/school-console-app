package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Course;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

@Transactional
@Repository
public class StudentCourseDao {
    @PersistenceContext
    private EntityManager em;
    private static final String GET_STUDENT_COURSES_NUM_FILEPATH = "src/main/resources/sql_queries/dao/student_courses/get_student_courses_num.sql";
    private static final String STUDENT_COURSES_EXISTANCE_FILEPATH = "src/main/resources/sql_queries/business_queries/student_course_exists.sql";
    private static final String GET_STUDENT_COURSES_NUM = QueryParser.parseQuery(GET_STUDENT_COURSES_NUM_FILEPATH);
    private static final String STUDENT_COURSES_EXISTANCE = QueryParser.parseQuery(STUDENT_COURSES_EXISTANCE_FILEPATH);

    public Integer countAll() {
        Long count = em.createQuery(GET_STUDENT_COURSES_NUM, Long.class).getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    public boolean exists(int studentId, int courseId) {
        Long count = em.createQuery(STUDENT_COURSES_EXISTANCE, Long.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getSingleResult();
        return count != null && count > 0;
    }

    public void save(int studentId, int courseId) {
        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);

        if (student == null) {
            throw new IllegalArgumentException("No student was found with given id");
        } else if (course == null) {
            throw new IllegalArgumentException("No course was found with given id");
        } else {
            student.getCourses().add(course);
        }
    }

    public void delete(int studentId, int courseId) {
        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);

        if (student != null && course != null) {
            student.getCourses().remove(course);
        }
    }
}
