package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudent;
import ua.foxminded.kucherenko.task2.queries.delete_student.DeleteStudentData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class DeleteStudentTest {
    @Autowired
    private DeleteStudent deleteStudent;
    @Autowired
    private StudentDao studentDao;

    @Test
    @Sql("/sample_data/students_samples.sql")
    void deleteStudent_ShouldntThrowException() {
        final int studentId = 4;
        DeleteStudentData data = new DeleteStudentData(studentId);
        deleteStudent.executeQuery(data);
        Assertions.assertFalse(() -> studentDao.get(studentId).isPresent());
    }

    @Test
    void deleteStudent_MissingStudent_ThrowException() {
        final int studentId = 250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.executeQuery(data));
    }

    @Test
    void deleteStudent_NegativeStudentId_ThrowException() {
        final int studentId = 250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> deleteStudent.executeQuery(data));
    }
}
