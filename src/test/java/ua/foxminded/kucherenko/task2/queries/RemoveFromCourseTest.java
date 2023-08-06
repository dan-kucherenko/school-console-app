package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.DBSetup;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class RemoveFromCourseTest {
    private final RemoveFromCourse removeFromCourse = new RemoveFromCourse();

    @BeforeAll
    static void initTestData() {
        DBSetup.initDatabase();
        DataGenerator generator = new DataGenerator();
        generator.generateData();
    }

    @Test
    void findStudent_ShouldntThrowException() {
        final String input = "25\n5";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertDoesNotThrow(removeFromCourse::executeOwnQuery);
    }

    @Test
    void findStudent_NonExistingStudent_ShouldThrowException() {
        final String input = "-25\n5\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, removeFromCourse::executeOwnQuery);
    }
    @Test
    void findStudent_NonExistingCourse_ShouldThrowException() {
        final String input = "25\n-5\n";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, removeFromCourse::executeOwnQuery);
    }
}
