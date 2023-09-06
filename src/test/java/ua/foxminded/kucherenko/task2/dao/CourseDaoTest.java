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

import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql","/database/clear_tables.sql" })
class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    @Sql("/sample_data/courses_samples.sql")
    void get() {
        final int courseId = 13;
        Optional<Course> course = courseDao.get(courseId);
        Assertions.assertFalse(course.isEmpty());
    }

    @Test
    @Sql( "/sample_data/courses_samples.sql")
    void getAll() {
        final int coursesListSize = 10;
        List<Course> allCourses = courseDao.getAll();
        Assertions.assertEquals(coursesListSize, allCourses.size());
    }


    @Test
    @Sql({"/database/clear_tables.sql", "/sample_data/courses_samples.sql"})
    void getAllCourseIds() {
        final int studentsIdListSize = 10;
        List<Integer> allStudentIds = courseDao.getAllCourseIds();
        Assertions.assertEquals(studentsIdListSize, allStudentIds.size());
    }

    @Test
    void save() {
        final Course course = new Course("TestCourse", "This is a test course");
        Assertions.assertTrue(courseDao.get(1).isEmpty());
        courseDao.save(course);
        Assertions.assertNotNull(courseDao.get(1));
    }

    @Test
    void update() {
        final Course course = new Course("TestCourse", "This is a test course");
        courseDao.save(course);
        final Optional<Course> addedCourse = courseDao.get(1);
        Assertions.assertNotNull(addedCourse);
        final String changedCourseName = "TestCourse1";
        final Course changedCourse = new Course(changedCourseName, "This is a test course");
        courseDao.update(1, changedCourse);
        Assertions.assertNotNull(courseDao.get(1));
    }

    @Test
    void delete() {
        final int addedCourseId = 1;
        final Course course = new Course("TestCourse", "This is a test course");
        courseDao.save(course);
        final Optional<Course> addedCourse = courseDao.get(1);
        Assertions.assertTrue(addedCourse.isPresent());
        courseDao.delete(addedCourseId);
        Assertions.assertTrue(courseDao.get(addedCourseId).isEmpty());
    }
}
