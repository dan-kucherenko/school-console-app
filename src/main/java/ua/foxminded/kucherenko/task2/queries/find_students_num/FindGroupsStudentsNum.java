package ua.foxminded.kucherenko.task2.queries.find_students_num;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import java.util.List;

@Component
public class FindGroupsStudentsNum implements IResultingQuery<List<GroupStudentsInfo>, FindGroupsStudentsNumData> {
    private final GroupDao groupDao;

    @Autowired
    public FindGroupsStudentsNum(JdbcTemplate jdbcTemplate) {
        this.groupDao = new GroupDao(jdbcTemplate);
    }

    @Override
    public List<GroupStudentsInfo> executeQueryWithRes(FindGroupsStudentsNumData data) {
        if (data.getStudentsQuantity() < 0) {
            throw new IllegalArgumentException("Number of students can't be less than 0");
        }

        return groupDao.getGroupByStudentNum(data.getStudentsQuantity());
    }
}
