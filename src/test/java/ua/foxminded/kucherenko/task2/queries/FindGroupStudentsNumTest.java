package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNum;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNumData;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql", "/sample_data/students_samples.sql",
        "/sample_data/groups_samples.sql"})
class FindGroupStudentsNumTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private FindGroupsStudentsNum findGroupsStudentsNum;

    @BeforeEach
    void setUp() {
        findGroupsStudentsNum = new FindGroupsStudentsNum(jdbcTemplate);
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
