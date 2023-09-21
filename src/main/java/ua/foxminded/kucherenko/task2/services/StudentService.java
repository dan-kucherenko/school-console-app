package ua.foxminded.kucherenko.task2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private static final int MAX_GROUP_CAPACITY = 30;
    private static final int NO_GROUP_INDEX = 0;

    public void addStudent(AddStudentData data) {
        if (data.getGroupId() < 0) {
            throw new IllegalArgumentException("GroupID should be a positive number");
        }
        Integer groupId = data.getGroupId() == NO_GROUP_INDEX ? null : data.getGroupId();
        if (groupId != null) {
            int groupQuantity = groupDao.getGroupQuantity(data.getGroupId());
            if (groupIsFull(groupQuantity)) {
                throw new IllegalArgumentException("Group is full");
            }
        }
        studentDao.save(new Student(groupId, data.getFirstName(), data.getLastName()));
        LOGGER.debug("Student {} {} from group {} was successfully added", data.getFirstName(), data.getLastName(), data.getGroupId());
    }

    public List<Student> findStudentsByCourse(FindStudentByCourseData data) {
        if (data.getCourseName().isBlank()) {
            throw new IllegalArgumentException("Course name can't be blank");
        }
        List<Student> resultList = studentDao.getByCourse(data.getCourseName());
        LOGGER.debug("Students by course {} was successfully found", data.getCourseName());
        return resultList;
    }

    public void deleteStudent(DeleteStudentData data) {
        if (data.getStudentId() <= 0 || studentDao.get(data.getStudentId()).isEmpty()) {
            throw new IllegalArgumentException("Student ID doesn't exist");
        }
        studentDao.delete(data.getStudentId());
        LOGGER.debug("Student with id {} was successfully deleted", data.getStudentId());
    }

    private boolean groupIsFull(int groupQuantity) {
        return groupQuantity >= MAX_GROUP_CAPACITY;
    }
}
