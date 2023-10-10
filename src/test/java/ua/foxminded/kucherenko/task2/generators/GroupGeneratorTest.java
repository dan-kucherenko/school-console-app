package ua.foxminded.kucherenko.task2.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.foxminded.kucherenko.task2.repositories.GroupRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class GroupGeneratorTest {
    @Autowired
    private GroupsGenerator groupsGenerator;
    @MockBean
    private GroupRepository groupRepository;

    @BeforeEach
    void generateGroups() {
        MockitoAnnotations.initMocks(this);
        groupsGenerator.addToDb();
    }

    @Test
    void checkNumberOfGroups(){
        final int groupListSize = 10;
        verify(groupRepository, times(groupListSize)).save(any());
    }
}
