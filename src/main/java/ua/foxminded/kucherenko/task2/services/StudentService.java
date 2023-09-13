package ua.foxminded.kucherenko.task2.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.services.service_utils.delete_student.DeleteStudentData;
import ua.foxminded.kucherenko.task2.services.service_utils.find_stud_by_course.FindStudentByCourseData;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private GroupDao groupDao;
    private static final Logger LOGGER = LogManager.getLogger(StudentService.class);

    public void addStudent(AddStudentData data) {
        if (data.getGroupId() < 0) {
            throw new IllegalArgumentException("GroupID should be a positive number");
        }
        int groupQuantity = groupDao.getGroupQuantity(data.getGroupId());
        if (groupDao.groupIsFull(groupQuantity)) {
            throw new IllegalArgumentException("Group is full");
        }
        studentDao.save(new Student(data.getGroupId(), data.getFirstName(), data.getLastName()));
        LOGGER.info("Student {} {} from group {} was successfully added", data.getFirstName(), data.getLastName(), data.getGroupId());
    }

    public List<Student> findStudentsByCourse(FindStudentByCourseData data) {
        if (data.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name can't be blank");
        }
        return studentDao.getByCourse(data.getCourseName());
    }

    public void deleteStudent(DeleteStudentData data) {
        if (data.getStudentId() <= 0 || studentDao.get(data.getStudentId()).isEmpty()) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }
        studentDao.delete(data.getStudentId());
        LOGGER.info("Student with id {} was successfully deleted", data.getStudentId());
    }
}
