package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.DBSetup;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class AddStudentTest {
    private final AddStudent addStudent = new AddStudent();

    @BeforeAll
    static void initTestData() {
        DBSetup.initDatabase();
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAdding() {
        final String input = "3\nJohn\nDoe\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertDoesNotThrow(addStudent::executeOwnQuery);
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final String input = "0\nJohn\nDoe\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertDoesNotThrow(addStudent::executeOwnQuery);
    }

    @Test
    void addStudent_GroupIdIsNegative_ShouldThrowException() {
        final String input = "-3\nJohn\nDoe\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, addStudent::executeOwnQuery);
    }

    @Test
    void addStudent_GroupIdIsBiggerThanTen_ShouldThrowException() {
        final String input = "15\nJohn\nDoe\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, addStudent::executeOwnQuery);
    }
}
