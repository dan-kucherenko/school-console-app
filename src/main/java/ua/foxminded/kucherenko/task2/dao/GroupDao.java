package ua.foxminded.kucherenko.task2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Group;
import ua.foxminded.kucherenko.task2.models.GroupStudentsInfo;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GroupDao implements Dao<Group> {
    @PersistenceContext
    private EntityManager em;
    private static final String GET_GROUP_BY_ID_FILEPATH = "src/main/resources/sql_queries/dao/group/get_group.sql";
    private static final String GET_ALL_GROUPS_FILEPATH = "src/main/resources/sql_queries/dao/group/get_all_groups.sql";
    private static final String GET_GROUPS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_groups_id.sql";
    private static final String GET_GROUPS_NUM_FILEPATH = "src/main/resources/sql_queries/dao/group/get_groups_num.sql";
    private static final String UPDATE_GROUP_FILEPATH = "src/main/resources/sql_queries/dao/group/update_group.sql";
    private static final String DELETE_GROUP_FILEPATH = "src/main/resources/sql_queries/dao/group/delete_group.sql";
    private static final String STUDENT_QUANTITY_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/get_student_group_quantity.sql";
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH = "src/main/resources/sql_queries/business_queries/find_groups_students_num.sql";

    private static final String GET_GROUP_BY_ID = QueryParser.parseQuery(GET_GROUP_BY_ID_FILEPATH);
    private static final String GET_ALL_GROUPS = QueryParser.parseQuery(GET_ALL_GROUPS_FILEPATH);
    private static final String GET_GROUPS_ID_QUERY = QueryParser.parseQuery(GET_GROUPS_ID_FILEPATH);
    private static final String GET_GROUPS_NUM_QUERY = QueryParser.parseQuery(GET_GROUPS_NUM_FILEPATH);
    private static final String UPDATE_GROUP = QueryParser.parseQuery(UPDATE_GROUP_FILEPATH);
    private static final String DELETE_GROUP = QueryParser.parseQuery(DELETE_GROUP_FILEPATH);
    private static final String STUDENT_QUANTITY_QUERY = QueryParser.parseQuery(STUDENT_QUANTITY_QUERY_FILEPATH);
    private static final String FIND_GROUPS_BY_STUDENTS_NUMBER = QueryParser.parseQuery(FIND_GROUPS_BY_STUDENTS_NUMBER_FILEPAPTH);

    @Override
    public Optional<Group> get(int id) {
        try {
            return Optional.ofNullable(em.createQuery(GET_GROUP_BY_ID, Group.class)
                    .setParameter("groupId", id)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<GroupStudentsInfo> getGroupByStudentNum(int studentsNum) {
        return em.createQuery(
                        FIND_GROUPS_BY_STUDENTS_NUMBER, GroupStudentsInfo.class)
                .setParameter("studentsNum", studentsNum)
                .getResultList();

        // TODO: remake hql for hibernate and check
    }

    @Override
    public List<Group> getAll() {
        return em.createQuery(GET_ALL_GROUPS, Group.class).getResultList();
    }

    @Override
    public Integer countAll() {
        Long count = em.createQuery(GET_GROUPS_NUM_QUERY, Long.class)
                .getSingleResult();
        return count == null ? 0 : count.intValue();
    }

    public List<Integer> getAllGroupIds() {
        return em.createQuery(GET_GROUPS_ID_QUERY, Integer.class).getResultList();
    }

    public int getGroupQuantity(int groupId) {
        Long result = em.createQuery(STUDENT_QUANTITY_QUERY, Long.class)
                .setParameter("groupId", groupId)
                .getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    @Override
    public void save(Group group) {
        em.persist(group);
    }

    @Override
    public void update(int id, Group group) {
        em.createQuery(UPDATE_GROUP)
                .setParameter("groupName", group.getGroupName())
                .setParameter("groupId", id)
                .executeUpdate();
    }

    @Override
    public void delete(int id) {
        em.createQuery(DELETE_GROUP)
                .setParameter("groupId", id)
                .executeUpdate();
    }
}
