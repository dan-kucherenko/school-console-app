package ua.foxminded.kucherenko.task2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.repositories.GroupRepository;
import ua.foxminded.kucherenko.task2.services.service_utils.find_students_num.FindGroupsStudentsNumData;

import java.util.List;

@Service
public class GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);
    private GroupRepository repository;
    @Autowired
    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public List<GroupStudentsInfo> findGroupsByStudNum(FindGroupsStudentsNumData data) {
        if (data.getStudentsQuantity() < 0) {
            throw new IllegalArgumentException("Number of students can't be less than 0");
        }
        List<GroupStudentsInfo> resultList = repository.getGroupByStudentNum(data.getStudentsQuantity());
        LOGGER.debug("Groups found!");
        return resultList;
    }
}
