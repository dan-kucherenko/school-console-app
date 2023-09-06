package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class AddStudentTest {
    @Autowired
    private AddStudent addStudent;
    @Autowired
    @Mock
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final int groupId = 0;
        final String firstName = "Petro";
        final String lastName = "Petrov";

        when(studentDao.getIdByName(firstName, lastName)).thenReturn(1);

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

        when(studentDao.getIdByName(firstName, lastName)).thenReturn(null);

        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudent.executeQuery(addStudentData));
        Assertions.assertFalse(() -> {
            Integer studentId = studentDao.getIdByName(firstName, lastName);
            return studentId != null;
        });
    }
}
