package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Course;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class StudentCourseDaoTest {
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private StudentDao studentDao;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql",
            "/sample_data/students_samples.sql", "/sample_data/student_courses_samples.sql"})
    void getAllStudentCourses() {
        final int studentCourseRecordsNum = 7;
        final Map<Integer, Set<Course>> expectedStudentCourses = Map.of(
                1, new HashSet<>(List.of(new Course(5, "History", "Study of past events and human societies."))),
                2, new HashSet<>(List.of(new Course(5, "History", "Study of past events and human societies."))),
                4, new HashSet<>(List.of(new Course(3, "Physics", "Study of matter, energy, and fundamental forces."),
                        new Course(5, "History", "Study of past events and human societies."))),
                5, new HashSet<>(List.of(new Course(6, "English", "Study of the English language and literature."))),
                6, new HashSet<>(List.of(new Course(5, "History", "Study of past events and human societies."))),
                7, new HashSet<>(List.of(new Course(5, "History", "Study of past events and human societies."))),
                8, new HashSet<>(List.of(new Course(5, "History", "Study of past events and human societies.")))
        );

        final Map<Integer, Set<Course>> studentCourses = studentCourseDao.getAll();

        Assertions.assertEquals(studentCourseRecordsNum, studentCourses.size());
        Assertions.assertEquals(expectedStudentCourses, studentCourses);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql",
            "/sample_data/students_samples.sql", "/sample_data/student_courses_samples.sql"})
    void countAllStudentCourses() {
        final int studentCourseRecordsNum = 8;
        Assertions.assertEquals(studentCourseRecordsNum, studentCourseDao.countAll());
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/students_samples.sql",
            "/sample_data/courses_samples.sql", "/sample_data/student_courses_samples.sql"})
    void studentCourseExists() {
        final int studentId = 4;
        final int courseId = 5;
        final boolean studentCourseExists = studentCourseDao.exists(studentId, courseId);
        Assertions.assertTrue(studentCourseExists);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/students_samples.sql",
            "/sample_data/courses_samples.sql"})
    void saveStudentCourse() {
        final int studentId = 4;
        final int courseId = 5;
        System.out.println(studentDao.getAll());
        Assertions.assertFalse(studentCourseDao.exists(studentId, courseId));
        studentCourseDao.save(studentId, courseId);
        Assertions.assertTrue(studentCourseDao.exists(studentId, courseId));
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql",
            "/sample_data/students_samples.sql", "/sample_data/insert_student_course.sql"})
    void deleteStudentCourse() {
        final int studentId = 1;
        final int courseId = 5;
        Assertions.assertTrue(studentCourseDao.exists(studentId, courseId));
        studentCourseDao.delete(studentId, courseId);
        Assertions.assertFalse(studentCourseDao.exists(studentId, courseId));
    }
}
