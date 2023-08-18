package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.data_generator.AddDataForTest;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseTestConfig;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourse;
import ua.foxminded.kucherenko.task2.queries.remove_from_course.RemoveFromCourseData;

class RemoveFromCourseTest {
    private static final DatabaseTestConfig testConfig = new DatabaseTestConfig();
    private final RemoveFromCourse removeFromCourse = new RemoveFromCourse(testConfig);

    @BeforeAll
    static void initTestData() {
        CreateTestDatabase.initDatabase();
        CreateTestTables.createTables();
        AddDataForTest testDataGenerator = new AddDataForTest();
        testDataGenerator.addStudents();
        testDataGenerator.addStudentCourses();
    }

    @Test
    void removeFromCourse_ShouldntThrowException() {
        final int studentId = 25;
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(studentId, courseId);

        Assertions.assertDoesNotThrow(() -> removeFromCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            StudentCourseExistence studentExistByNameQuery = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentExistByNameQuery.executeQueryWithRes();
        });
    }

    @Test
    void removeFromCourse_NonExistingStudent_ShouldThrowException() {
        final Integer studentId = new StudentExistByNameQuery("Petro", "Petrovych", testConfig).executeQueryWithRes();
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(studentId, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }

    @Test
    void removeFromCourse_NonExistingCourse_ShouldThrowException() {
        final int studentId = 25;
        final int courseId = -5;
        RemoveFromCourseData data = new RemoveFromCourseData(studentId, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> removeFromCourse.executeQuery(data));
    }
}
