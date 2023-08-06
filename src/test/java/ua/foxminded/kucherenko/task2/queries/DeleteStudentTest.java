package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.DBSetup;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class DeleteStudentTest {
    private final DeleteStudent deleteStudent = new DeleteStudent();

    @BeforeAll
    static void initTestData() {
        DBSetup.initDatabase();
        DataGenerator generator = new DataGenerator();
        generator.generateData();
    }

    @Test
    void deleteStudent_ShouldntThrowException() {
        final int groupId = 15;
        final String input = Integer.toString(groupId);
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertDoesNotThrow(deleteStudent::executeOwnQuery);
    }

    @Test
    void deleteStudent_MissingStudent_ThrowException() {
        final String input = "250";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, deleteStudent::executeOwnQuery);
    }

    @Test
    void deleteStudent_NegativeStudentId_ThrowException() {
        final String input = "-250";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, deleteStudent::executeOwnQuery);
    }
}
