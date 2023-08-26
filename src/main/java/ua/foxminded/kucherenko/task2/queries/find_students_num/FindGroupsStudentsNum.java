package ua.foxminded.kucherenko.task2.queries.find_students_num;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import javax.sql.DataSource;
import java.util.List;

@Component
public class FindGroupsStudentsNum implements IResultingQuery<List<GroupStudentsInfo>, FindGroupsStudentsNumData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH = "src/main/resources/sql_queries/business_queries/find_groups_students_num.sql";
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER = QueryParser.parseQuery(FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH);

    @Autowired
    public FindGroupsStudentsNum(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<GroupStudentsInfo> executeQueryWithRes(FindGroupsStudentsNumData data) {
        if (data.getStudentsQuantity() < 0) {
            throw new IllegalArgumentException("Number of students can't be less than 0");
        }

        return jdbcTemplate.query(
                FIND_GROUPS_BY_STUDENTS_NUMBER,
                new Object[]{data.getStudentsQuantity()},
                new BeanPropertyRowMapper<>(GroupStudentsInfo.class)
        );
    }
}
