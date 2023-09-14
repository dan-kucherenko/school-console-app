package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

@Component
public class CheckDbEmptiness {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private StudentCourseDao studentCourseDao;


    public boolean isStudentsTableEmpty() {
        return studentDao.getAll().isEmpty();
    }

    public boolean isCoursesTableEmpty() {
        return courseDao.getAll().isEmpty();
    }

    public boolean isGroupsTableEmpty() {
        return groupDao.getAll().isEmpty();
    }

    public boolean isStudentCoursesTableEmpty() {
        return studentCourseDao.getAll().isEmpty();
    }
}
