package ua.foxminded.kucherenko.task2.queries.remove_from_course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class RemoveFromCourse implements IVoidQuery<RemoveFromCourseData> {
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public void executeQuery(RemoveFromCourseData data) {
        if (data.getCourseId() <= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }

        Integer studentId = studentDao.getIdByName(data.getFirstName(), data.getLastName());
        if (studentId == null) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }

        studentCourseDao.delete(studentId, data.getCourseId());
    }
}
