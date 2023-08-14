//package ua.foxminded.kucherenko.task2.queries;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
//import ua.foxminded.kucherenko.task2.generators.DataGenerator;
//import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//class AddStudentToCourseTest {
//    private final AddStudentToCourse addStudentToCourse = new AddStudentToCourse();
//
//    @BeforeAll
//    static void initTestData() {
//        CreateTestDatabase.initDatabase();
//        DataGenerator generator = new DataGenerator();
//        generator.generateData();
//    }
//
//    @Test
//    void addStudentToCourse_ShouldntThrowException() {
//        final String input = "150\n5\n";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertDoesNotThrow(addStudentToCourse::executeQuery);
//    }
//
//    @Test
//    void addStudentToCourse_MissingStudent_ThrowsException() {
//        final String input = "240\n5\n";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertThrows(IllegalArgumentException.class, addStudentToCourse::executeQuery);
//    }
//
//    @Test
//    void addStudentToCourse_MissingCourseId_ThrowsException() {
//        final String input = "20\n50\n";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertThrows(IllegalArgumentException.class, addStudentToCourse::executeQuery);
//    }
//
//    @Test
//    void addStudentToCourse_NegativeInput_ThrowsException() {
//        final String input = "-20\n-50\n";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertThrows(IllegalArgumentException.class, addStudentToCourse::executeQuery);
//    }
//}
