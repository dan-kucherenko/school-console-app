package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

@Configuration
public class CheckDbEmptiness {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String STUDENTS_IS_EMPTY_FILEPATH = "src/main/resources/sql_queries/database/student_table_empty.sql";
    private static final String COURSES_IS_EMPTY_FILEPATH = "src/main/resources/sql_queries/database/student_table_empty.sql";
    private static final String GROUPS_IS_EMPTY_FILEPATH = "src/main/resources/sql_queries/database/student_table_empty.sql";
    private static final String STUDENT_COURSES_IS_EMPTY_FILEPATH = "src/main/resources/sql_queries/database/student_table_empty.sql";

    private static final String STUDENTS_IS_EMPTY = QueryParser.parseQuery(STUDENTS_IS_EMPTY_FILEPATH);
    private static final String COURSES_IS_EMPTY = QueryParser.parseQuery(COURSES_IS_EMPTY_FILEPATH);
    private static final String GROUPS_IS_EMPTY = QueryParser.parseQuery(GROUPS_IS_EMPTY_FILEPATH);
    private static final String STUDENT_COURSES_IS_EMPTY = QueryParser.parseQuery(STUDENT_COURSES_IS_EMPTY_FILEPATH);


    public boolean isStudentsTableEmpty() {
        Integer rowCount = jdbcTemplate.queryForObject(STUDENTS_IS_EMPTY, Integer.class);
        return rowCount != null && rowCount == 0;
    }

    public boolean isCoursesTableEmpty() {
        Integer rowCount = jdbcTemplate.queryForObject(COURSES_IS_EMPTY, Integer.class);
        return rowCount != null && rowCount == 0;
    }

    public boolean isGroupsTableEmpty() {
        Integer rowCount = jdbcTemplate.queryForObject(GROUPS_IS_EMPTY, Integer.class);
        return rowCount != null && rowCount == 0;
    }

    public boolean isStudentCoursesTableEmpty() {
        Integer rowCount = jdbcTemplate.queryForObject(STUDENT_COURSES_IS_EMPTY, Integer.class);
        return rowCount != null && rowCount == 0;
    }
}
