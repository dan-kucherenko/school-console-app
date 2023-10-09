package ua.foxminded.kucherenko.task2.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.repositories.GroupRepository;
import ua.foxminded.kucherenko.task2.services.service_utils.find_students_num.FindGroupsStudentsNumData;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class GroupServiceTest {
    @MockBean
    private GroupRepository groupRepository;
    @Autowired
    private GroupService groupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findGroups_NoGroupsReturned_ShouldntThrowException() {
        final int studentsQuantity = 2;
        FindGroupsStudentsNumData data = new FindGroupsStudentsNumData(studentsQuantity);
        when(groupRepository.getGroupByStudentNum(studentsQuantity)).thenReturn(new ArrayList<>());
        List<GroupStudentsInfo> actualRes = groupService.findGroupsByStudNum(data);

        Assertions.assertDoesNotThrow(() -> groupService.findGroupsByStudNum(data));
        Assertions.assertTrue(actualRes.isEmpty());
    }

    @Test
    void findGroups_NegativeStudenNumber_ThrowException() {
        final int studentsQuantity = -5;
        FindGroupsStudentsNumData data = new FindGroupsStudentsNumData(studentsQuantity);
        Assertions.assertThrows(IllegalArgumentException.class, () -> groupService.findGroupsByStudNum(data));
    }
}
