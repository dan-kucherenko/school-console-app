package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.StudentCourse;

import java.util.List;

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
        final int studentCourseRecordsNum = 8;
        final List<StudentCourse> expectedStudentCourses = List.of(
                new StudentCourse(1, 5),
                new StudentCourse(2, 5),
                new StudentCourse(4, 3),
                new StudentCourse(4, 5),
                new StudentCourse(5, 6),
                new StudentCourse(6, 5),
                new StudentCourse(7, 5),
                new StudentCourse(8, 5)
        );
        final List<StudentCourse> studentCourses = studentCourseDao.getAll();

        Assertions.assertEquals(studentCourseRecordsNum, studentCourses.size());
        Assertions.assertEquals(expectedStudentCourses, studentCourses);
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
        final StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        Assertions.assertFalse(studentCourseDao.exists(studentId, courseId));
        studentCourseDao.save(studentCourse);
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
