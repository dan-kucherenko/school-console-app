package ua.foxminded.kucherenko.task2.generators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;
import ua.foxminded.kucherenko.task2.services.StudentCoursesService;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course.AddStudentToCourseData;

import java.util.*;

@Component
public class StudentCourseGenerator implements IGenerator {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentCoursesService studentCoursesService;
    private static final int MAX_STUDENT_COURSES_NUM = 3;

    @Override
    public void addToDb() {
        Random random = new Random();
        Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();

        List<Integer> availableStudentsIds = studentRepository.getAllStudentIds();

        List<Integer> availableCoursesIds = courseRepository.getAllCourseIds();

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
                final String firstName = studentRepository.findById(studentId).get().getFirstName();
                final String lastName = studentRepository.findById(studentId).get().getLastName();
                studentCoursesService.addStudentToCourse(new AddStudentToCourseData(firstName, lastName, courseId));
            }
        }
    }
}