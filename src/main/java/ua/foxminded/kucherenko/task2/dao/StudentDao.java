package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class StudentDao implements Dao<Student> {
    @PersistenceContext
    private EntityManager em;
    private static final String GET_STUDENTS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_students_id.sql";
    private static final String GET_STUDENTS_NUM_FILEPATH = "src/main/resources/sql_queries/dao/student/get_students_num.sql";
    private static final String UPDATE_STUDENT_FILEPATH = "src/main/resources/sql_queries/dao/student/update_student.sql";
    private static final String FIND_STUDENT_BY_COURSE_FILEPATH = "src/main/resources/sql_queries/business_queries/find_student_by_course.sql";
    private static final String STUDENT_ID_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exist_by_name.sql";

    private static final String GET_STUDENTS_ID_QUERY = QueryParser.parseQuery(GET_STUDENTS_ID_FILEPATH);
    private static final String GET_STUDENTS_NUM_QUERY = QueryParser.parseQuery(GET_STUDENTS_NUM_FILEPATH);
    private static final String UPDATE_STUDENT = QueryParser.parseQuery(UPDATE_STUDENT_FILEPATH);
    private static final String FIND_STUDENT_BY_COURSE = QueryParser.parseQuery(FIND_STUDENT_BY_COURSE_FILEPATH);
    private static final String STUDENT_ID_QUERY = QueryParser.parseQuery(STUDENT_ID_QUERY_FILEPATH);

    @Override
    public Optional<Student> get(int id) {
        try {
            return Optional.ofNullable(em.find(Student.class, id));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Student> getByCourse(String courseName) {
        try {
            return em.createQuery(FIND_STUDENT_BY_COURSE, Student.class)
                    .setParameter("courseName", courseName)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
        // TODO: remake the hql for hibernate and check the method
    }

    public Integer getIdByName(String firstName, String lastName) {
        Integer id;
        try {
            id = em.createQuery(STUDENT_ID_QUERY, Integer.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getSingleResult();
            return id;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Student> getAll() {
        return em.createQuery("FROM Student", Student.class)
                .getResultList();
    }

    @Override
    public Integer countAll() {
        Long count = em.createQuery(GET_STUDENTS_NUM_QUERY, Long.class)
                .getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    public List<Integer> getAllStudentIds() {
        return em.createQuery(GET_STUDENTS_ID_QUERY, Integer.class).getResultList();
    }

    @Override
    public void save(Student student) {
        em.persist(student);
    }

    @Override
    public void update(int id, Student student) {
        Student existingStudent = em.find(Student.class, id);
//        existingStudent.
        em.createQuery(UPDATE_STUDENT)
                .setParameter("groupId", student.getGroupId())
                .setParameter("firstName", student.getFirstName())
                .setParameter("lastName", student.getLastName())
                .setParameter("studentId", id)
                .executeUpdate();
    }

    @Override
    public void delete(int id) {
        Student existingStudent = em.find(Student.class, id);
        em.remove(existingStudent);
    }
}
