package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseTestConfig;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseData;

class AddStudentToCourseTest {
    private final static DatabaseTestConfig testConfig = new DatabaseTestConfig();
    private final AddStudentToCourse addStudentToCourse = new AddStudentToCourse(testConfig);

    @BeforeAll
    static void initTestData() {
        CreateTestDatabase.initDatabase();
        CreateTestTables.createTables();
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
        AddStudentToCourseData data = new AddStudentToCourseData(studentId, courseId);

        Assertions.assertDoesNotThrow(() -> addStudentToCourse.executeQuery(data));
        Assertions.assertTrue(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_MissingStudent_ThrowsException() {
        final Integer studentId = new StudentExistByNameQuery("Royal", "Marines", testConfig).executeQueryWithRes();
        final int courseId = 6;
        AddStudentToCourseData data = new AddStudentToCourseData(studentId, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_MissingCourseId_ThrowsException() {
        final int studentId = 5;
        final int courseId = 60;
        AddStudentToCourseData data = new AddStudentToCourseData(studentId, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }

    @Test
    void addStudentToCourse_NegativeInput_ThrowsException() {
        final int studentId = -5;
        final int courseId = -6;
        AddStudentToCourseData data = new AddStudentToCourseData(studentId, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            StudentCourseExistence studentCourseExistence = new StudentCourseExistence(studentId, courseId, testConfig);
            return studentCourseExistence.executeQueryWithRes();
        });
    }
}
