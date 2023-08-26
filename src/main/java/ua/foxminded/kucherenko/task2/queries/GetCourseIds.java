package ua.foxminded.kucherenko.task2.queries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import javax.sql.DataSource;
import java.util.List;

@Component
public class GetCourseIds {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_COURSES_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/get_all_groups_id.sql";
    private static final String GET_COURSES_ID_QUERY = QueryParser.parseQuery(GET_COURSES_ID_FILEPATH);

    @Autowired
    public GetCourseIds(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Integer> executeQueryWithRes() {
        return jdbcTemplate.query(
                GET_COURSES_ID_QUERY,
                (resultSet, rowNum) -> resultSet.getInt("course_id")
        );
    }
}
