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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class CourseDaoTest {
    @Autowired
    private CourseDao courseDao;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void getCourseById() {
        final int courseId = 1;
        final String courseName = "Math";
        final String courseDescription = "Study of numbers, quantities, and shapes.";

        Optional<Course> course = courseDao.get(courseId);
        System.out.println(courseDao.getAll());
        Assertions.assertFalse(course.isEmpty());
        Assertions.assertEquals(course.get().getCourseName(), courseName);
        Assertions.assertEquals(course.get().getCourseDescription(), courseDescription);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void getAllCourses() {
        final int coursesListSize = 10;
        List<Course> expectedAllCourses = List.of(
                new Course(1, "Math", "Study of numbers, quantities, and shapes."),
                new Course(2, "Biology", "Study of living organisms and their interactions."),
                new Course(3, "Physics", "Study of matter, energy, and fundamental forces."),
                new Course(4, "Chemistry", "Study of substances, their properties, and reactions."),
                new Course(5, "History", "Study of past events and human societies."),
                new Course(6, "English", "Study of the English language and literature."),
                new Course(7, "Computer Science", "Study of computation and information processing."),
                new Course(8, "Geography", "Study of the Earth's landscapes and environments."),
                new Course(9, "Art", "Study of visual arts and creative expression."),
                new Course(10, "Music", "Study of musical theory, composition, and performance.")
        );
        List<Course> allCourses = courseDao.getAll();
        Assertions.assertEquals(coursesListSize, allCourses.size());
        Assertions.assertEquals(expectedAllCourses, allCourses);
    }


    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void getAllCourseIds() {
        final int studentsIdListSize = 10;
        Integer[] studentIds = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> allStudentIds = courseDao.getAllCourseIds();
        Assertions.assertEquals(studentsIdListSize, allStudentIds.size());
        Assertions.assertEquals(Arrays.stream(studentIds).toList(), allStudentIds);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql"})
    void saveCourse() {
        final Course course = new Course("TestCourse", "This is a test course");
        Assertions.assertTrue(courseDao.getAll().isEmpty());
        courseDao.save(course);
        List<Course> allCourses = courseDao.getAll();
        Assertions.assertTrue(allCourses.contains(course));
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/insert_course.sql"})
    void updateCourseById() {
        final Optional<Course> addedCourse = courseDao.get(1);
        Assertions.assertNotNull(addedCourse);
        final String changedCourseName = "TestCourse1";
        final Course changedCourse = new Course(changedCourseName, "This is a test course");
        courseDao.update(1, changedCourse);
        Assertions.assertNotNull(courseDao.get(1));
    }

    @Test
    @Sql("/sample_data/insert_course.sql")
    void deleteCourseById() {
        final int addedCourseId = 1;
        courseDao.delete(addedCourseId);
        Assertions.assertTrue(courseDao.get(addedCourseId).isEmpty());
    }
}
