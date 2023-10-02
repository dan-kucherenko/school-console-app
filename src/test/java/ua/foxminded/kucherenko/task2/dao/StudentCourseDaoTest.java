package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

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
