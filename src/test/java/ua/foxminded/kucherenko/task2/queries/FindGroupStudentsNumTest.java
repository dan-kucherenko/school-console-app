//package ua.foxminded.kucherenko.task2.queries;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import ua.foxminded.kucherenko.task2.db.CreateTestDatabase;
//import ua.foxminded.kucherenko.task2.generators.DataGenerator;
//import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNum;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//
//class FindGroupStudentsNumTest {
//    private final FindGroupsStudentsNum findGroupsStudentsNum = new FindGroupsStudentsNum();
//
//    @BeforeAll
//    static void initTestData() {
//        CreateTestDatabase.initDatabase();
//        DataGenerator generator = new DataGenerator();
//        generator.generateData();
//    }
//
//    @Test
//    void findGroups_ShouldntThrowException() {
//        final String input = "15";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertDoesNotThrow(findGroupsStudentsNum::executeQueryWithRes);
//    }
//
//    @Test
//    void findGroups_NegativeStudentNumber_ThrowException() {
//        final String input = "-250";
//        final InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//
//        Assertions.assertThrows(IllegalArgumentException.class, findGroupsStudentsNum::executeQueryWithRes);
//    }
//}
