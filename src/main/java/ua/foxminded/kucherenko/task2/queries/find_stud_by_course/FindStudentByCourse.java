package ua.foxminded.kucherenko.task2.queries.find_stud_by_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.queries.IResultingQuery;

import java.util.List;

@Component
public class FindStudentByCourse implements IResultingQuery<List<Student>, FindStudentByCourseData> {
    private final StudentDao studentDao;

    @Autowired
    public FindStudentByCourse(JdbcTemplate jdbcTemplate) {
        this.studentDao = new StudentDao(jdbcTemplate);
    }

    @Override
    public List<Student> executeQueryWithRes(FindStudentByCourseData data) {
        if (data.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name can't be blank");
        }

        return studentDao.getByCourse(data.getCourseName());
    }
}
