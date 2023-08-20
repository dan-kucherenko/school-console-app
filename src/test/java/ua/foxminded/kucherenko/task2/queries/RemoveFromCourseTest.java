package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.data_generator.AddDataForTest;
import ua.foxminded.kucherenko.task2.db.*;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourse;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourseData;

class RemoveFromCourseTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final RemoveFromCourse removeFromCourse = new RemoveFromCourse(testConfig);

    @BeforeAll
    static void initTestData() {
        DbInit.initDatabase();
        AddDataForTest testDataGenerator = new AddDataForTest();
        testDataGenerator.addStudents();
        testDataGenerator.addStudentCourses();
    }

    @Test
    void removeFromCourse_ShouldntThrowException() {
        final String firstName = "Emma";
        final String lastName = "Emmson";
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertDoesNotThrow(() -> removeFromCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            Integer studentId = new StudentIdByNameQuery(firstName, lastName, testConfig).executeQueryWithRes();
            StudentCourseExistence studentExistByNameQuery = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentExistByNameQuery.executeQueryWithRes();
        });
    }

    @Test
    void removeFromCourse_NonExistingStudent_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }

    @Test
    void removeFromCourse_NonExistingCourse_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = -5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }
}
