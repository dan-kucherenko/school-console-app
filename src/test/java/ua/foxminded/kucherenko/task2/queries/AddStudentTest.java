package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class AddStudentTest {
    @Autowired
    private AddStudent addStudent;
    @Autowired
    private StudentDao studentDao;

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final int groupId = 0;
        final String firstName = "Petro";
        final String lastName = "Petrov";

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> addStudent.executeQuery(addStudentData));
        Assertions.assertTrue(() -> {
            Integer studentId = studentDao.getIdByName(firstName, lastName);
            return studentId != null;
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
            Integer studentId = studentDao.getIdByName(firstName, lastName);
            return studentId != null;
        });
    }
}
