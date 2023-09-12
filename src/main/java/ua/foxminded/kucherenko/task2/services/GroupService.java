package ua.foxminded.kucherenko.task2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.queries.find_students_num.FindGroupsStudentsNumData;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupDao groupDao;

    public List<GroupStudentsInfo> findGroupsByStudNum(FindGroupsStudentsNumData data) {
        if (data.getStudentsQuantity() < 0) {
            throw new IllegalArgumentException("Number of students can't be less than 0");
        }
        return groupDao.getGroupByStudentNum(data.getStudentsQuantity());
    }
}
