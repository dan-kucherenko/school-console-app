package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseData;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class AddStudentToCourseTest {
    @Autowired
    private AddStudentToCourse addStudentToCourse;
    @Mock
    private StudentDao studentDao;
    @Mock
    private StudentCourseDao studentCourseDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStudentToCourse_MissingStudent_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final Integer studentId = studentDao.getIdByName(firstName, lastName);
        final int courseId = 6;

        when(studentCourseDao.exists(studentId, courseId)).thenReturn(false);

        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> studentCourseDao.exists(studentId, courseId));
    }

    @Test
    void addStudentToCourse_MissingCourseId_ThrowsException() {
        final String firstName = "Michael1";
        final String lastName = "Michaelson1";
        final int courseId = 60;
        final Integer studentId = studentDao.getIdByName(firstName, lastName);

        when(studentCourseDao.exists(studentId, courseId)).thenReturn(false);

        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> studentCourseDao.exists(studentId, courseId));
    }

    @Test
    void addStudentToCourse_NegativeInput_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final int courseId = -6;
        final Integer studentId = studentDao.getIdByName(firstName, lastName);

        when(studentCourseDao.exists(studentId, courseId)).thenReturn(false);

        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> studentCourseDao.exists(studentId, courseId));
    }
}
