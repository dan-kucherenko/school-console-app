package ua.foxminded.kucherenko.task2.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course.AddStudentToCourseData;
import ua.foxminded.kucherenko.task2.services.service_utils.remove_from_course.RemoveFromCourseData;

import java.util.Collections;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class StudentCourseServiceTest {
    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private StudentCoursesService studentCoursesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addStudentToCourse_MissingStudent_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        when(studentRepository.getIdByName(firstName, lastName)).thenReturn(Collections.emptyList());
        final int courseId = 6;

        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> studentCoursesService.addStudentToCourse(data));
    }

    @Test
    void removeFromCourse_NonExistingStudent_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = 5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);
        when(studentRepository.getIdByName(firstName, lastName)).thenReturn(Collections.emptyList());

        Assertions.assertThrows(IllegalArgumentException.class, () -> studentCoursesService.removeStudentFromCourse(data));
    }

    @Test
    void removeFromCourse_NonExistingCourse_ShouldThrowException() {
        final String firstName = "Petro";
        final String lastName = "Petrovych";
        final int courseId = -5;
        RemoveFromCourseData data = new RemoveFromCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> studentCoursesService.removeStudentFromCourse(data));
    }
}
