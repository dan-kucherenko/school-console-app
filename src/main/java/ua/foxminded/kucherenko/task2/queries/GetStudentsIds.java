package ua.foxminded.kucherenko.task2.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import javax.sql.DataSource;
import java.util.List;

@Component
public class GetStudentsIds {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_STUDENTS_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_students_id.sql";
    private static final String GET_STUDENTS_ID_QUERY = QueryParser.parseQuery(GET_STUDENTS_ID_FILEPATH);

    @Autowired
    public GetStudentsIds(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Integer> executeQueryWithRes() {
        return jdbcTemplate.query(
                GET_STUDENTS_ID_QUERY,
                (resultSet, rowNum) -> resultSet.getInt("student_id")
        );
    }
}
