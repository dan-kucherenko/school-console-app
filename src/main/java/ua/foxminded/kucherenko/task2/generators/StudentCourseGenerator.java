package ua.foxminded.kucherenko.task2.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.dao.CourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.models.StudentCourse;

import java.util.*;

@Component
public class StudentCourseGenerator implements IGenerator {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudentCourseDao studentCourseDao;
    private static final int MAX_STUDENT_COURSES_NUM = 3;

    @Override
    public void addToDb() {
        Random random = new Random();
        Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();

        List<Integer> availableStudentsIds = studentDao.getAllStudentIds();

        List<Integer> availableCoursesIds = courseDao.getAllCourseIds();

        for (Integer studentId : availableStudentsIds) {
            Set<Integer> studentCourses = studentCourseMap.computeIfAbsent(studentId, k -> new HashSet<>());
            int numberOfCourses = random.nextInt(MAX_STUDENT_COURSES_NUM) + 1;
            numberOfCourses = Math.min(numberOfCourses, availableCoursesIds.size() - studentCourses.size());

            while (studentCourses.size() < numberOfCourses) {
                int courseIndex = random.nextInt(availableCoursesIds.size());
                studentCourses.add(availableCoursesIds.get(courseIndex));
            }
            studentCourseMap.put(studentId, studentCourses);
            for (int courseId : studentCourses) {
                studentCourseDao.save(new StudentCourse(studentId, courseId));
            }
        }
    }
}