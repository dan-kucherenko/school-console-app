package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

import static org.mockito.Mockito.*;

@SpringBootTest
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
    void checkStudentsNumber() {
        final int studentsListSize = 200;
        verify(studentDao, times(studentsListSize)).save(any());
    }
}
