package ua.foxminded.kucherenko.task2.repositories;

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

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class CourseRepositoryTest {
    @Autowired
    private CourseRepository repository;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void getCourseById() {
        final int courseId = 1;
        final String courseName = "Math";
        final String courseDescription = "Study of numbers, quantities, and shapes.";

        final Course course = repository.findById(courseId).get();
        Assertions.assertFalse(repository.findById(courseId).isEmpty());
        Assertions.assertEquals(course.getCourseName(), courseName);
        Assertions.assertEquals(course.getCourseDescription(), courseDescription);
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
        List<Course> allCourses = repository.findAll();
        Assertions.assertEquals(coursesListSize, allCourses.size());
        Assertions.assertEquals(expectedAllCourses, allCourses);
    }


    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void getAllCourseIds() {
        final int coursesIdListSize = 10;
        Integer[] coursesIds = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> allSCoursesIds = repository.getAllCourseIds();
        Assertions.assertEquals(coursesIdListSize, allSCoursesIds.size());
        Assertions.assertEquals(Arrays.stream(coursesIds).toList(), allSCoursesIds);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/courses_samples.sql"})
    void countAllCourses() {
        final int coursesNumber = 10;
        Assertions.assertEquals(coursesNumber, repository.count());
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql"})
    void saveCourse() {
        final Course course = new Course("TestCourse", "This is a test course");
        Assertions.assertTrue(repository.findAll().isEmpty());
        repository.save(course);
        List<Course> allCourses = repository.findAll();
        Assertions.assertTrue(allCourses.contains(course));
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/insert_course.sql"})
    void updateCourseById() {
        final Course addedCourse = repository.findById(1).get();
        Assertions.assertNotNull(addedCourse);
        final String changedCourseName = "TestCourse1";
        final String courseDescription = "This is a test course";
        final Course changedCourse = new Course(1, changedCourseName, courseDescription);
        repository.save(changedCourse);

        final Course changedCourseFromDb = repository.findById(1).get();
        Assertions.assertTrue(repository.findById(1).isPresent());
        Assertions.assertEquals(changedCourseFromDb.getCourseName(), changedCourseName);
        Assertions.assertEquals(changedCourseFromDb.getCourseDescription(), courseDescription);
    }

    @Test
    @Sql("/sample_data/insert_course.sql")
    void deleteCourseById() {
        final int addedCourseId = 1;
        repository.deleteById(addedCourseId);
        Assertions.assertTrue(repository.findById(addedCourseId).isEmpty());
    }
}
