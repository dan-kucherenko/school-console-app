package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Group;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void getGroupById() {
        final int groupId = 1;
        final String groupName = "OC-26";
        final Optional<Group> group = groupDao.get(groupId);
        Assertions.assertFalse(group.isEmpty());
        Assertions.assertEquals(group.get().getGroupName(), groupName);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void getAllGroups() {
        final int groupsNumber = 10;
        final List<Group> expectedGroups = List.of(
                new Group("OC-26"),
                new Group("AH-94"),
                new Group("NO-69"),
                new Group("KL-46"),
                new Group("PN-91"),
                new Group("ZT-54"),
                new Group("CA-79"),
                new Group("CF-85"),
                new Group("QY-95"),
                new Group("HV-40")
        );
        final List<Group> groups = groupDao.getAll();
        Assertions.assertEquals(groupsNumber, groups.size());
        Assertions.assertEquals(expectedGroups, groups);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void getAllGroupIds() {
        final int groupsNumber = 10;
        final Integer[] expectedGroupIds = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final List<Integer> groupsIds = groupDao.getAllGroupIds();
        Assertions.assertEquals(groupsNumber, groupsIds.size());
        Assertions.assertEquals(Arrays.stream(expectedGroupIds).toList(), groupsIds);
    }


    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql"})
    void saveGroup() {
        final Group group = new Group("TestGroup");
        final int addedGroupId = 1;
        Assertions.assertTrue(groupDao.get(addedGroupId).isEmpty());
        groupDao.save(group);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }

    @Test
    @Sql("/sample_data/insert_group.sql")
    void updateGroup() {
        final int addedGroupId = 1;
        Assertions.assertNotNull(groupDao.get(addedGroupId));
        final Group changedGroup = new Group("TestGroup1");
        groupDao.update(addedGroupId, changedGroup);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }

    @Test
    @Sql("/sample_data/insert_group.sql")
    void deleteGroup() {
        final int addedGroupId = 1;
        groupDao.delete(addedGroupId);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }
}
