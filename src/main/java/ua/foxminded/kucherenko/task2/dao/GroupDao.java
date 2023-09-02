package ua.foxminded.kucherenko.task2.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Group;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupDao implements Dao<Group> {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_GROUP_BY_ID_FILEPATH = "src/main/resources/sql_queries/dao/group/get_group.sql";
    private static final String GET_ALL_GROUPS_FILEPATH = "src/main/resources/sql_queries/dao/group/get_all_groups.sql";
    private static final String ADD_GROUP_FILEPATH = "src/main/resources/sql_queries/dao/group/add_group.sql";
    private static final String UPDATE_GROUP_FILEPATH = "src/main/resources/sql_queries/dao/group/update_group.sql";
    private static final String DELETE_GROUP_FILEPATH = "src/main/resources/sql_queries/dao/group/delete_group.sql";
    private static final String STUDENT_QUANTITY_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/get_student_group_quantity.sql";
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH = "src/main/resources/sql_queries/business_queries/find_groups_students_num.sql";

    private static final String GET_GROUP_BY_ID = QueryParser.parseQuery(GET_GROUP_BY_ID_FILEPATH);
    private static final String GET_ALL_GROUPS = QueryParser.parseQuery(GET_ALL_GROUPS_FILEPATH);
    private static final String ADD_GROUP = QueryParser.parseQuery(ADD_GROUP_FILEPATH);
    private static final String UPDATE_GROUP = QueryParser.parseQuery(UPDATE_GROUP_FILEPATH);
    private static final String DELETE_GROUP = QueryParser.parseQuery(DELETE_GROUP_FILEPATH);
    private static final String STUDENT_QUANTITY_QUERY = QueryParser.parseQuery(STUDENT_QUANTITY_QUERY_FILEPATH);
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER = QueryParser.parseQuery(FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH);


    public GroupDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Group> get(int id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(GET_GROUP_BY_ID, new Object[]{id}, Group.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<GroupStudentsInfo> getGroupByStudentNum(int studentsNum) {
        return jdbcTemplate.query(
                FIND_GROUPS_BY_STUDENTS_NUMBER,
                new Object[]{studentsNum},
                new BeanPropertyRowMapper<>(GroupStudentsInfo.class)
        );
    }

    @Override
    public List<Group> getAll() {
        try {
            return jdbcTemplate.queryForList(GET_ALL_GROUPS, Group.class);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public int getGroupQuantity(int groupId) {
        return jdbcTemplate.queryForObject(STUDENT_QUANTITY_QUERY, Integer.class, groupId);
    }

    @Override
    public void save(Group group) {
        jdbcTemplate.update(ADD_GROUP, group.getGroupName());
    }

    @Override
    public void update(int id, Group group) {
        jdbcTemplate.update(UPDATE_GROUP, group.getGroupName(), id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP, id);
    }
}
