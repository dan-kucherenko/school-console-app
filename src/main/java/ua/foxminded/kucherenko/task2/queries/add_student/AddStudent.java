package ua.foxminded.kucherenko.task2.queries.add_student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

import javax.sql.DataSource;

@Component
public class AddStudent implements IVoidQuery<AddStudentData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_STUDENT_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/insert_student.sql";
    private static final String ADD_STUDENT_QUERY = QueryParser.parseQuery(ADD_STUDENT_QUERY_FILEPATH);
    private static final String STUDENT_QUANTITY_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/get_student_group_quantity.sql";
    private static final String STUDENT_QUANTITY_QUERY = QueryParser.parseQuery(STUDENT_QUANTITY_QUERY_FILEPATH).trim();

    @Autowired
    public AddStudent(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void executeQuery(AddStudentData data) {
        int groupQuantity = getGroupQuantity(data.getGroupId());

        if (data.getGroupId() < 0 || groupIsFull(groupQuantity)) {
            throw new IllegalArgumentException("GroupID should be a positive number or group is full");
        }

        try {
            jdbcTemplate.update(
                    ADD_STUDENT_QUERY,
                    data.getGroupId() == 0 ? null : data.getGroupId(),
                    data.getFirstName(),
                    data.getLastName()
            );

            System.out.println("User " + data.getFirstName() + ' ' + data.getLastName() + " from group " +
                    data.getGroupId() + " was successfully added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getGroupQuantity(int groupId) {
        return jdbcTemplate.queryForObject(STUDENT_QUANTITY_QUERY, Integer.class, groupId);
    }

    private boolean groupIsFull(int groupQuantity) {
        return groupQuantity >= 30;
    }
}
