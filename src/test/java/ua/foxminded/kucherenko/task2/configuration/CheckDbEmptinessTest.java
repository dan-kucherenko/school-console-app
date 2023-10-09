package ua.foxminded.kucherenko.task2.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;
import ua.foxminded.kucherenko.task2.repositories.GroupRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CheckDbEmptinessTest {

    @Autowired
    private CheckDbEmptiness checkDbEmptiness;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private GroupRepository groupRepository;

    @Test
    void isStudentsTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(studentRepository.findAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isStudentsTableEmpty());
    }

    @Test
    void isCoursesTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(courseRepository.findAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isCoursesTableEmpty());
    }

    @Test
    void isGroupsTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(groupRepository.findAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isGroupsTableEmpty());
    }
}
