package ua.foxminded.kucherenko.task2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.StudentCourse;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course.AddStudentToCourseData;
import ua.foxminded.kucherenko.task2.services.service_utils.remove_from_course.RemoveFromCourseData;

@Service
public class StudentCoursesService {
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private StudentDao studentDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCoursesService.class);

    public void addStudentToCourse(AddStudentToCourseData data) {
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
        LOGGER.debug("Student with id {} was successfully added to course {}", studentId, data.getCourseId());
    }

    public void removeStudentFromCourse(RemoveFromCourseData data) {
        if (data.getCourseId()<= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }
        Integer studentId = studentDao.getIdByName(data.getFirstName(), data.getLastName());
        if (studentId == null) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }
        studentCourseDao.delete(studentId, data.getCourseId());
        LOGGER.debug("Student with id {} was successfully removed from course {}", studentId, data.getCourseId());
    }
}
