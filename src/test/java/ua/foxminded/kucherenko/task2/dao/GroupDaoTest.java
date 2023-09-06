package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Group;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    @Sql("/sample_data/groups_samples.sql")
    void get() {
        final int groupId = 22;
        final Optional<Group> group = groupDao.get(groupId);
        Assertions.assertFalse(group.isEmpty());
    }

    @Test
    @Sql("/sample_data/groups_samples.sql")
    void getAll() {
        final int groupsNumber = 10;
        final List<Group> groups = groupDao.getAll();
        Assertions.assertEquals(groupsNumber, groups.size());
    }

    @Test
    @Sql({"/database/clear_tables.sql", "/sample_data/groups_samples.sql"})
    void getAllGroupIds() {
        final int groupsNumber = 10;
        final List<Integer> groups = groupDao.getAllGroupIds();
        Assertions.assertEquals(groupsNumber, groups.size());
    }


    @Test
    void save() {
        final Group group = new Group("TestGroup");
        final int addedGroupId = 1;
        Assertions.assertTrue(groupDao.get(addedGroupId).isEmpty());
        groupDao.save(group);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }

    @Test
    void update() {
        final Group group = new Group("TestGroup");
        final int addedGroupId = 1;
        groupDao.save(group);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
        final Group changedGroup = new Group("TestGroup1");
        groupDao.update(addedGroupId, changedGroup);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }

    @Test
    void delete() {
        final Group group = new Group("TestGroup");
        final int addedGroupId = 1;
        groupDao.save(group);
        groupDao.delete(addedGroupId);
        Assertions.assertNotNull(groupDao.get(addedGroupId));
    }
}
