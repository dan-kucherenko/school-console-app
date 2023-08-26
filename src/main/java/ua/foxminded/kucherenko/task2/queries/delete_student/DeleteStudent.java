package ua.foxminded.kucherenko.task2.queries.delete_student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.parser.QueryParser;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;
import ua.foxminded.kucherenko.task2.queries.StudentExistByIdQuery;

import javax.sql.DataSource;

@Component
public class DeleteStudent implements IVoidQuery<DeleteStudentData> {
    private final JdbcTemplate jdbcTemplate;
    private static final String DELETE_STUDENT_BY_ID_FILEPATH = "src/main/resources/sql_queries/business_queries/delete_by_student_id.sql";
    private static final String DELETE_STUDENT_BY_ID = QueryParser.parseQuery(DELETE_STUDENT_BY_ID_FILEPATH);

    @Autowired
    public DeleteStudent(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void executeQuery(DeleteStudentData data) {
        StudentExistByIdQuery studentExistence = new StudentExistByIdQuery(jdbcTemplate.getDataSource());
        if (data.getStudentId() <= 0 || !studentExistence.executeQueryWithRes(data.getStudentId())) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }

        int affectedRows = jdbcTemplate.update(DELETE_STUDENT_BY_ID, data.getStudentId());

        if (affectedRows > 0) {
            System.out.println("Student with id " + data.getStudentId() + " was successfully deleted");
        }
    }
}
