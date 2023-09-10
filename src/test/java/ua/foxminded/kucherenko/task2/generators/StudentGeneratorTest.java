package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class StudentGeneratorTest {
    @Autowired
    private StudentsGenerator studentsGenerator;
    @MockBean
    private StudentDao studentDao;

    @BeforeEach
    void generateStudents() {
        MockitoAnnotations.initMocks(this);
        studentsGenerator.addToDb();
    }

    @Test
    @Sql("/database/create_tables.sql")
    void checkStudentsNumber() {
        final int studentsListSize = 200;
        verify(studentDao, times(studentsListSize)).save(any());
    }
}
