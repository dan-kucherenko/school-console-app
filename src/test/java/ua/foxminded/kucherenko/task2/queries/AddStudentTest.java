package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.*;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;

class AddStudentTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final AddStudent addStudent = new AddStudent(testConfig);

    @BeforeAll
    static void initTestData() {
        DbInit.initDatabase();
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
