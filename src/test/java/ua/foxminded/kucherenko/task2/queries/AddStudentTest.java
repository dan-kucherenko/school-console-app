package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
import ua.foxminded.kucherenko.task2.db.CreateTestTables;
import ua.foxminded.kucherenko.task2.db.DatabaseTestConfig;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;

class AddStudentTest {
    private final DatabaseTestConfig testConfig = new DatabaseTestConfig();
    private final AddStudent addStudent = new AddStudent(testConfig);
    private final Integer notExistingId = null;

    @BeforeAll
    static void initTestData() {
        CreateTestDatabase.initDatabase();
        CreateTestTables.createTables();
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final int groupId = 0;
        final String firstName = "Petro";
        final String lastName = "Petrov";

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> addStudent.executeQuery(addStudentData));
        Assertions.assertTrue(() -> {
            StudentExistByNameQuery studentExistByNameQuery = new StudentExistByNameQuery(firstName, lastName, testConfig);
            return studentExistByNameQuery.executeQueryWithRes() != notExistingId;
        });
    }

    @Test
    void addStudent_GroupIsBiggerThanTen_ShouldThrowsException() {
        final int groupId = 20;
        final String firstName = "Igor";
        final String lastName = "Igoriv";

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudent.executeQuery(addStudentData));
        Assertions.assertFalse(() -> {
            StudentExistByNameQuery studentExistByNameQuery = new StudentExistByNameQuery(firstName, lastName, testConfig);
            return studentExistByNameQuery.executeQueryWithRes() != notExistingId;
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
            StudentExistByNameQuery studentExistByNameQuery = new StudentExistByNameQuery(firstName, lastName, testConfig);
            return studentExistByNameQuery.executeQueryWithRes() != notExistingId;
        });
    }
}
