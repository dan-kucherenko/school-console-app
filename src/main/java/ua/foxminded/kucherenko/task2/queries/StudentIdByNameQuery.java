package ua.foxminded.kucherenko.task2.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import javax.sql.DataSource;

@Component
public class StudentIdByNameQuery {
    private final JdbcTemplate jdbcTemplate;
    private static final String STUDENT_EXISTS_QUERY_FILEPATH = "src/main/resources/sql_queries/business_queries/student_exist_by_name.sql";
    private static final String STUDENT_EXISTS_QUERY = QueryParser.parseQuery(STUDENT_EXISTS_QUERY_FILEPATH);

    @Autowired
    public StudentIdByNameQuery(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Integer executeQueryWithRes(String studentFirstName, String studentLastName) {
        return jdbcTemplate.queryForObject(
                STUDENT_EXISTS_QUERY,
                new Object[]{studentFirstName, studentLastName},
                (resultSet, rowNum) -> resultSet.getInt("student_id")
        );
    }
}
