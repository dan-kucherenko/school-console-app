package ua.foxminded.kucherenko.task2.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.services.service_utils.delete_student.DeleteStudentData;
import ua.foxminded.kucherenko.task2.services.service_utils.find_stud_by_course.FindStudentByCourseData;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class StudentServiceTest {
    @MockBean
    private StudentDao studentDao;
    @Autowired
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStudent_ShouldNotThrowExceptionWhileAddingWithNullGroup() {
        final int groupId = 0;
        final String firstName = "Petro";
        final String lastName = "Petrov";
        AddStudentData addStudentData = new AddStudentData(groupId, firstName, lastName);
        Assertions.assertDoesNotThrow(() -> studentService.addStudent(addStudentData));
        Assertions.assertTrue(() -> {
            when(studentDao.getIdByName(firstName, lastName)).thenReturn(1);
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
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(addStudentData));
        Assertions.assertFalse(() -> {
            when(studentDao.getIdByName(firstName, lastName)).thenReturn(null);
            Integer studentId = studentDao.getIdByName(firstName, lastName);
            return studentId != null;
        });
    }

    @Test
    void findStudentByCourse_EmptyList_ShouldntThrowException() {
        final String courseName = "Music";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        when(studentDao.getByCourse(courseName)).thenReturn(new ArrayList<>());
        List<Student> actualRes = studentService.findStudentsByCourse(data);
        Assertions.assertTrue(actualRes.isEmpty());
    }

    @Test
    void findStudentByCourse_MissingCourse_ShouldntThrowException() {
        final String courseName = " ";
        FindStudentByCourseData data = new FindStudentByCourseData(courseName);
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.findStudentsByCourse(data));
    }

    @Test
    void deleteStudent_MissingStudent_ThrowException() {
        final int studentId = 250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(data));
    }

    @Test
    void deleteStudent_NegativeStudentId_ThrowException() {
        final int studentId = -250;
        DeleteStudentData data = new DeleteStudentData(studentId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentService.deleteStudent(data));
    }
}
