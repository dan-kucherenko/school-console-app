package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.TestConfigReader;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudent;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudentData;

class DeleteStudentTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final DeleteStudent deleteStudent = new DeleteStudent(testConfig);

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
    void deleteStudent_ShouldntThrowException() {
        final int studentId = 4;
        DeleteStudentData data = new DeleteStudentData(studentId);
        deleteStudent.executeQuery(data);
        Assertions.assertFalse(() -> {
            StudentExistByIdQuery studentExistByIdQuery = new StudentExistByIdQuery(studentId, testConfig);
            return studentExistByIdQuery.executeQueryWithRes();
        });
    }

    @Test
    void deleteStudent_MissingStudent_ThrowException() {
        final int studentId = 250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.executeQuery(data));
    }

    @Test
    void deleteStudent_NegativeStudentId_ThrowException() {
        final int studentId = 250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.executeQuery(data));
    }
}
