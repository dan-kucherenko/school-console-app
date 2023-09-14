package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.dao.GroupDao;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;

@Component
public class CheckDbEmptiness {
    private static final int EMPTY_DB_RES = 0;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private StudentCourseDao studentCourseDao;


    public boolean isStudentsTableEmpty() {
        return studentDao.countAll() == EMPTY_DB_RES;
    }

    public boolean isCoursesTableEmpty() {
        return courseDao.countAll() == EMPTY_DB_RES;
    }

    public boolean isGroupsTableEmpty() {
        return groupDao.countAll() == EMPTY_DB_RES;
    }

    public boolean isStudentCoursesTableEmpty() {
        return studentCourseDao.countAll() == EMPTY_DB_RES;
    }
}
