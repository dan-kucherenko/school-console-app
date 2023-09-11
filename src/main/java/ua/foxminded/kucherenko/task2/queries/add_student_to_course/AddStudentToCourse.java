package ua.foxminded.kucherenko.task2.queries.add_student_to_course;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.queries.IVoidQuery;

@Component
public class AddStudentToCourse implements IVoidQuery<AddStudentToCourseData> {
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private StudentDao studentDao;
    private static final Logger LOGGER = LogManager.getLogger(AddStudentToCourse.class);


    @Override
    public void executeQuery(AddStudentToCourseData data) {
        if (data.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course id: it should be more than 0");
        }

        Integer studentId = studentDao.getIdByName(data.getFirstName(), data.getLastName());
        if (studentId == null) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesn't exist");
        }

        if (studentCourseDao.exists(studentId, data.getCourseId())) {
            throw new IllegalArgumentException("This record already exists");
        }

        studentCourseDao.save(new StudentCourse(studentId, data.getCourseId()));
        LOGGER.info("Student with id " + studentId + " was successfully added to course " + data.getCourseId());
    }
}
