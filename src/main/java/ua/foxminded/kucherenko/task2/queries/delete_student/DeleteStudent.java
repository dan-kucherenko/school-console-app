package ua.foxminded.kucherenko.task2.queries.delete_student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class DeleteStudent implements IVoidQuery<DeleteStudentData> {
    private final StudentDao studentDao;

    @Autowired
    public DeleteStudent(JdbcTemplate jdbcTemplate) {
        this.studentDao = new StudentDao(jdbcTemplate);
    }

    @Override
    public void executeQuery(DeleteStudentData data) {
        if (data.getStudentId() <= 0 || studentDao.get(data.getStudentId()).isEmpty()) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }

        studentDao.delete(data.getStudentId());
        System.out.println("Student with id " + data.getStudentId() + " was successfully deleted");
    }
}
