package ua.foxminded.kucherenko.task2.repositories;

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

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class GroupRepositoryTest {
    @Autowired
    private GroupRepository repository;

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void getGroupById() {
        System.out.println(repository.findAll());
        final int groupId = 1;
        final String groupName = "OC-26";
        final Group group = repository.findById(groupId).get();
        Assertions.assertFalse(repository.findById(groupId).isEmpty());
        Assertions.assertEquals(group.getGroupName(), groupName);
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
        final List<Group> groups = repository.findAll();
        Assertions.assertEquals(groupsNumber, groups.size());
        Assertions.assertEquals(expectedGroups, groups);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void getAllGroupIds() {
        final int groupsNumber = 10;
        final Integer[] expectedGroupIds = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final List<Integer> groupsIds = repository.getAllGroupIds();
        Assertions.assertEquals(groupsNumber, groupsIds.size());
        Assertions.assertEquals(Arrays.stream(expectedGroupIds).toList(), groupsIds);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/groups_samples.sql"})
    void countAllGroups() {
        final int groupsNumber = 10;
        Assertions.assertEquals(groupsNumber, repository.count());
    }


    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql"})
    void saveGroup() {
        final String groupName = "TestGroup";
        final Group group = new Group(groupName);
        final int addedGroupId = 1;
        Assertions.assertTrue(repository.findById(addedGroupId).isEmpty());
        repository.save(group);

        final Group savedGroup = repository.findById(addedGroupId).get();
        Assertions.assertTrue(repository.findById(addedGroupId).isPresent());
        Assertions.assertEquals(savedGroup.getGroupId(), addedGroupId);
        Assertions.assertEquals(savedGroup.getGroupName(), groupName);
    }

    @Test
    @Sql("/sample_data/insert_group.sql")
    void updateGroup() {
        final int addedGroupId = 1;
        final String updatedGroupName = "TestGroup1";
        Assertions.assertNotNull(repository.findById(addedGroupId));
        final Group changedGroup = new Group(addedGroupId, updatedGroupName);
        repository.save(changedGroup);

        final Group changedGroupFromDb = repository.findById(addedGroupId).get();
        Assertions.assertTrue(repository.findById(addedGroupId).isPresent());
        Assertions.assertEquals(changedGroupFromDb.getGroupId(), addedGroupId);
        Assertions.assertEquals(changedGroupFromDb.getGroupName(), updatedGroupName);
    }

    @Test
    @Sql("/sample_data/insert_group.sql")
    void deleteGroup() {
        final int addedGroupId = 1;
        repository.deleteById(addedGroupId);
        Assertions.assertFalse(repository.findById(addedGroupId).isPresent());
    }
}
