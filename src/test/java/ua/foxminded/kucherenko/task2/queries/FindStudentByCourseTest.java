package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.data_generator.AddDataForTest;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseTestConfig;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourse;
import ua.foxminded.kucherenko.task2.queries.find_stud_by_course.FindStudentByCourseData;


import java.util.List;

class FindStudentByCourseTest {
    private static final DatabaseTestConfig testConfig = new DatabaseTestConfig();
    private final FindStudentByCourse findStudentByCourse = new FindStudentByCourse(testConfig);

    @BeforeAll
    static void initTestData() {
        CreateTestDatabase.initDatabase();
        CreateTestTables.createTables();
        AddDataForTest testDataGenerator = new AddDataForTest();
        testDataGenerator.addStudents();
        testDataGenerator.addStudentCourses();
    }

    @Test
    void findStudentByCourse_ShouldntThrowException() {
        final String courseName = "Physics";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        List<Student> expectedRes = List.of(
                new Student(4, 4, "Alice", "Alison")
        );
        List<Student> actualRes = findStudentByCourse.executeQueryWithRes(data);


        Assertions.assertEquals(expectedRes, actualRes);
    }

    @Test
    void findStudentByCourse_EmptyList_ShouldntThrowException() {
        final String courseName = "Music";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        List<Student> actualRes = findStudentByCourse.executeQueryWithRes(data);

        Assertions.assertTrue(actualRes.isEmpty());
    }

    @Test
    void findStudentByCourse_MissingCourse_ShouldntThrowException() {
        final String courseName = " ";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);

        Assertions.assertThrows(IllegalArgumentException.class, () -> findStudentByCourse.executeQueryWithRes(data));
    }
}
