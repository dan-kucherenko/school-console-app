package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.TestConfigReader;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;

class AddStudentTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final AddStudent addStudent = new AddStudent(testConfig);

    @BeforeAll
    static void initTestData() {
        DatabaseConfig adminTestConfig = reader.readAdminConfiguration();
        CreateTestDatabase createTestDatabase = new CreateTestDatabase(adminTestConfig);
        createTestDatabase.initDatabase();
        CreateTestTables createTestTables = new CreateTestTables(testConfig);
        createTestTables.createTables();
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final int groupId = 0;
        final String firstName = "Petro";
        final String lastName = "Petrov";

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> addStudent.executeQuery(addStudentData));
        Assertions.assertTrue(() -> {
            StudentIdByNameQuery studentExistByNameQuery = new StudentIdByNameQuery(firstName, lastName, testConfig);
            return studentExistByNameQuery.executeQueryWithRes() != null;
        });
    }

    @Test
    void addStudent_GroupIsNegative_ShouldThrowsException() {
        final int groupId = -5;
        final String firstName = "Dmytro";
        final String lastName = "Dmytrov";

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudent.executeQuery(addStudentData));
        Assertions.assertFalse(() -> {
            StudentIdByNameQuery studentExistByNameQuery = new StudentIdByNameQuery(firstName, lastName, testConfig);
            return studentExistByNameQuery.executeQueryWithRes() != null;
        });
    }
}
