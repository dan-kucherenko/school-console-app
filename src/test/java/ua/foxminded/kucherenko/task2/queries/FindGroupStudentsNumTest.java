package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.kucherenko.task2.data_generator.AddDataForTest;
import ua.foxminded.kucherenko.task2.db.*;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNum;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNumData;

import java.util.List;

class FindGroupStudentsNumTest {
    private static final TestConfigReader reader = new TestConfigReader();
    private static final DatabaseConfig testConfig = reader.readTestSchoolAdminConfiguration();
    private final FindGroupsStudentsNum findGroupsStudentsNum = new FindGroupsStudentsNum(testConfig);

    @BeforeAll
    static void initTestData() {
        DbInit.initDatabase();
        AddDataForTest dataAdder = new AddDataForTest();
        dataAdder.addStudents();
    }

    @Test
    void findGroups_ShouldntThrowException() {
        final int studentsQuantity = 4;
        FindGroupsStudentsNumData data = new FindGroupsStudentsNumData(studentsQuantity);

        List<GroupStudentsInfo> actualRes = findGroupsStudentsNum.executeQueryWithRes(data);

        Assertions.assertDoesNotThrow(() -> findGroupsStudentsNum.executeQueryWithRes(data));
        Assertions.assertEquals(2, actualRes.size());
    }

    @Test
    void findGroups_NoGroupsReturned_ShouldntThrowException() {
        final int studentsQuantity = 2;
        FindGroupsStudentsNumData data = new FindGroupsStudentsNumData(studentsQuantity);

        List<GroupStudentsInfo> actualRes = findGroupsStudentsNum.executeQueryWithRes(data);

        Assertions.assertDoesNotThrow(() -> findGroupsStudentsNum.executeQueryWithRes(data));
        Assertions.assertTrue(actualRes.isEmpty());
    }

    @Test
    void findGroups_NegativeStudenNumber_ThrowException() {
        final int studentsQuantity = -5;
        FindGroupsStudentsNumData data = new FindGroupsStudentsNumData(studentsQuantity);

        Assertions.assertThrows(IllegalArgumentException.class, () -> findGroupsStudentsNum.executeQueryWithRes(data));
    }
}
