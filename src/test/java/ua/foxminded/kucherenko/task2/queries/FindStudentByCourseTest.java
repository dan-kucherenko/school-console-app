package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.db.DBSetup;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class FindStudentByCourseTest {
    private final FindStudentByCourse findStudentByCourse = new FindStudentByCourse();

    @BeforeAll
    static void initTestData() {
        DBSetup.initDatabase();
        DataGenerator generator = new DataGenerator();
        generator.generateData();
    }

    @Test
    void findStudentByCourse_ShouldntThrowException() {
        final String input = "Geography";
        final InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Assertions.assertDoesNotThrow(findStudentByCourse::executeQueryWithRes);
    }
}
