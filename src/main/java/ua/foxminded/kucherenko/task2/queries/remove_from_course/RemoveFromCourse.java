package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class RemoveFromCourse implements IVoidQuery<RemoveFromCourseData> {
    private final JdbcTemplate jdbcTemplate;
    private final StudentCourseDao studentCourseDao;

    @Autowired
    public RemoveFromCourse(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentCourseDao = new StudentCourseDao(jdbcTemplate);
    }

    @Override
    public void executeQuery(RemoveFromCourseData data) {
        StudentDao studentDao = new StudentDao(jdbcTemplate);
        Integer studentId = studentDao.getIdByName(data.getFirstName(), data.getLastName());

        if (studentId == null) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }

        if (data.getCourseId() <= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }
        studentCourseDao.delete(studentId, data.getCourseId());
    }
}
