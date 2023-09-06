package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.StudentCourse;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/drop_tables.sql"})
class StudentCourseDaoTest {
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private StudentDao studentDao;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql", "/sample_data/students_samples.sql", "/sample_data/student_courses_samples.sql"})
    void getAll() {
        final int studentCourseRecordsNum = 8;
        final List<StudentCourse> studentCourses = studentCourseDao.getAll();
        Assertions.assertEquals(studentCourseRecordsNum, studentCourses.size());
    }

    @Test
    @Sql({"/sample_data/students_samples.sql", "/sample_data/courses_samples.sql"})
    void exists() {
        final int studentId = 4;
        final int courseId = 5;
        final boolean studentCourseExists = studentCourseDao.exists(studentId, courseId);
        Assertions.assertTrue(studentCourseExists);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/students_samples.sql", "/sample_data/courses_samples.sql", "/sample_data/students_samples.sql"})
    void save() {
        final int studentId = 4;
        final int courseId = 5;
        System.out.println(studentDao.getAll());
        final StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        Assertions.assertFalse(studentCourseDao.exists(studentId, courseId));
        studentCourseDao.save(studentCourse);
        Assertions.assertTrue(studentCourseDao.exists(studentId, courseId));
    }

    @Test
    @Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
    @Sql({"/sample_data/students_samples.sql", "/sample_data/courses_samples.sql", "/sample_data/student_courses_samples.sql"})
    void delete() {
        final int studentId = 5;
        final int courseId = 7;
        final StudentCourse studentCourse = new StudentCourse(studentId, courseId);
        studentCourseDao.save(studentCourse);
        Assertions.assertTrue(studentCourseDao.exists(studentId, courseId));
        studentCourseDao.delete(studentId, courseId);
        Assertions.assertFalse(studentCourseDao.exists(studentId, courseId));
    }
}
