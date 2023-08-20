package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.TestConfigReader;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseData;

class AddStudentToCourseTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final AddStudentToCourse addStudentToCourse = new AddStudentToCourse(testConfig);

    @BeforeAll
    static void initTestData() {
        DatabaseConfig adminTestConfig = reader.readAdminConfiguration();
        CreateTestDatabase createTestDatabase = new CreateTestDatabase(adminTestConfig);
        createTestDatabase.initDatabase();
        CreateTestTables createTestTables = new CreateTestTables(testConfig);
        createTestTables.createTables();
        DataGenerator generator = new DataGenerator();
        generator.generateData(testConfig);
    }

    @Test
    void addStudentToCourse_ShouldntThrowException() {
        final int groupId = 4;
        final String studentFirstName = "Daniill";
        final String studentLastName = "Kucherenko";
        AddStudentData studentData = new AddStudentData(groupId, studentFirstName, studentLastName);
        AddStudent addStudent = new AddStudent(testConfig);
        addStudent.executeQuery(studentData);

        final int studentId = new StudentExistByNameQuery(studentFirstName, studentLastName, testConfig).executeQueryWithRes();
        final int courseId = 6;
        AddStudentToCourseData data = new AddStudentToCourseData(studentFirstName, studentLastName, courseId);

        Assertions.assertDoesNotThrow(() -> addStudentToCourse.executeQuery(data));
        Assertions.assertTrue(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_MissingStudent_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final Integer studentId = new StudentExistByNameQuery(firstName, lastName, testConfig).executeQueryWithRes();
        final int courseId = 6;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_MissingCourseId_ThrowsException() {
        final String firstName = "Michael1";
        final String lastName = "Michaelson1";
        final int courseId = 60;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            final Integer studentId = new StudentExistByNameQuery(firstName, lastName, testConfig).executeQueryWithRes();
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_NegativeInput_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final int courseId = -6;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            final Integer studentId = new StudentExistByNameQuery(firstName, lastName, testConfig).executeQueryWithRes();
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }
}
