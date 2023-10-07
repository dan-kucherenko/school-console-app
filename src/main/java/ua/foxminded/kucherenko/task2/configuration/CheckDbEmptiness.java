package ua.foxminded.kucherenko.task2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;
import ua.foxminded.kucherenko.task2.repositories.GroupRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentCourseRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;
import ua.foxminded.kucherenko.task2.services.StudentCoursesService;

@Component
public class CheckDbEmptiness {
    private static final int EMPTY_DB_RES = 0;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;


    public boolean isStudentsTableEmpty() {
        return studentRepository.count() == EMPTY_DB_RES;
    }

    public boolean isCoursesTableEmpty() {
        return courseRepository.count() == EMPTY_DB_RES;
    }

    public boolean isGroupsTableEmpty() {
        return groupRepository.count() == EMPTY_DB_RES;
    }

    public boolean isStudentCoursesTableEmpty() {
        return studentCourseRepository.count() == EMPTY_DB_RES;
    }
}
