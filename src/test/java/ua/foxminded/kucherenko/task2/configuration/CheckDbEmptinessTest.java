package ua.foxminded.kucherenko.task2.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class CheckDbEmptinessTest {

    @Autowired
    private CheckDbEmptiness checkDbEmptiness;

    @MockBean
    private StudentDao studentDao;

    @MockBean
    private CourseDao courseDao;

    @MockBean
    private GroupDao groupDao;

    @MockBean
    private StudentCourseDao studentCourseDao;

    @Test
    void isStudentsTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(studentDao.getAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isStudentsTableEmpty());
    }

    @Test
    void isCoursesTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(courseDao.getAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isCoursesTableEmpty());
    }

    @Test
    void isGroupsTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(groupDao.getAll()).thenReturn(List.of());
        assertTrue(checkDbEmptiness.isGroupsTableEmpty());
    }

    @Test
    void isStudentCoursesTableEmpty_DaoReturnsEmptyList_ShouldReturnTrue() {
        when(studentCourseDao.getAll()).thenReturn(Map.of());
        assertTrue(checkDbEmptiness.isStudentCoursesTableEmpty());
    }
}
