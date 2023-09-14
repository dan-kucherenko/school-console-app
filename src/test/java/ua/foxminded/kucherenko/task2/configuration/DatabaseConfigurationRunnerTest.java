package ua.foxminded.kucherenko.task2.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.generators.DataGenerator;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseConfigurationRunnerTest {
    @Autowired
    private DatabaseConfigurationRunner databaseConfigurationRunner;
    @MockBean
    private CheckDbEmptiness dbEmptinessChecker;
    @MockBean
    private DataGenerator dataGenerator;

    @Test
    void dbIsEmpty_Run() throws Exception {
        when(dbEmptinessChecker.isStudentsTableEmpty()).thenReturn(true);
        when(dbEmptinessChecker.isCoursesTableEmpty()).thenReturn(true);
        when(dbEmptinessChecker.isGroupsTableEmpty()).thenReturn(true);
        when(dbEmptinessChecker.isStudentCoursesTableEmpty()).thenReturn(true);
        databaseConfigurationRunner.run(null);
        verify(dataGenerator).generateData();
    }

    @Test
    void dbIsNotEmpty_Run() throws Exception {
        when(dbEmptinessChecker.isStudentsTableEmpty()).thenReturn(false);
        databaseConfigurationRunner.run(null);
        verify(dataGenerator, never()).generateData();
    }

    @Test
    void dbIsPartlyEmpty_Run() throws Exception {
        when(dbEmptinessChecker.isStudentsTableEmpty()).thenReturn(false);
        when(dbEmptinessChecker.isCoursesTableEmpty()).thenReturn(true);
        when(dbEmptinessChecker.isGroupsTableEmpty()).thenReturn(false);
        when(dbEmptinessChecker.isStudentCoursesTableEmpty()).thenReturn(true);
        databaseConfigurationRunner.run(null);
        verify(dataGenerator, never()).generateData();
    }
}
