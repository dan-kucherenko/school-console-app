package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class AddStudentToCourse implements IVoidQuery<AddStudentToCourseData> {
    private final JdbcTemplate jdbcTemplate;
    private final StudentCourseDao studentCourseDao;

    @Autowired
    public AddStudentToCourse(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentCourseDao = new StudentCourseDao(jdbcTemplate);
    }

    @Override
    public void executeQuery(AddStudentToCourseData data) {
        StudentDao studentDao = new StudentDao(jdbcTemplate);
        Integer studentId = studentDao.getIdByName(data.getFirstName(), data.getLastName());
        if (studentId == null) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesn't exist");
        }
        if (data.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course id: it should be more than 0");
        }

        if (studentCourseDao.exists(studentId, data.getCourseId())) {
            throw new IllegalArgumentException("This record already exists");
        }

        studentCourseDao.save(new StudentCourse(studentId, data.getCourseId()));
        System.out.println("Student with id " + studentId + " was successfully added to course " + data.getCourseId());
    }
}
