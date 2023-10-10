package ua.foxminded.kucherenko.task2.services;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.kucherenko.task2.models.Course;
import ua.foxminded.kucherenko.task2.models.Student;
import ua.foxminded.kucherenko.task2.repositories.CourseRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentCourseRepository;
import ua.foxminded.kucherenko.task2.repositories.StudentRepository;
import ua.foxminded.kucherenko.task2.services.service_utils.add_student_to_course.AddStudentToCourseData;
import ua.foxminded.kucherenko.task2.services.service_utils.remove_from_course.RemoveFromCourseData;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentCoursesService {
    private StudentRepository studentRepository;
    private StudentCourseRepository studentCourseRepository;
    private CourseRepository courseRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCoursesService.class);

    @Autowired
    public StudentCoursesService(StudentRepository studentRepository,
                                 StudentCourseRepository studentCourseRepository,
                                 CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
    }

    public void addStudentToCourse(AddStudentToCourseData data) {
        if (data.getCourseId() <= 0) {
            throw new IllegalArgumentException("Invalid course id: it should be more than 0");
        }
        List<Integer> studentIds = studentRepository.getIdByName(data.getFirstName(), data.getLastName());
        Integer studentId = studentIds.isEmpty() ? null : studentIds.get(0);
        if (studentId == null) {
            throw new IllegalArgumentException("Invalid student id: student id is less than 0 or student doesn't exist");
        }
        if (studentCourseRepository.exists(studentId, data.getCourseId())) {
            throw new IllegalArgumentException("This record already exists");
        }
        save(studentId, data.getCourseId());
        LOGGER.debug("Student with id {} was successfully added to course {}", studentId, data.getCourseId());
    }

    public void removeStudentFromCourse(RemoveFromCourseData data) {
        if (data.getCourseId() <= 0 || data.getCourseId() > 10) {
            throw new IllegalArgumentException("Course Id should be between 1 and 10");
        }
        List<Integer> studentIds = studentRepository.getIdByName(data.getFirstName(), data.getLastName());
        if (studentIds.isEmpty()) {
            throw new IllegalArgumentException("Student doesn't exist or the studentId is incorrect");
        }
        Integer studentId = studentIds.get(0);

        delete(studentId, data.getCourseId());
        LOGGER.debug("Student with id {} was successfully removed from course {}", studentId, data.getCourseId());
    }

//    private boolean exists(int studentId, int courseId) {
//        Optional<Student> student = studentRepository.findById(studentId);
//        student.orElseThrow(() -> new IllegalArgumentException("Student with given id wasn't found"));
//        Optional<Course> course = courseRepository.findById(courseId);
//        course.orElseThrow(() -> new IllegalArgumentException("Course with given id wasn't found"));
//        return student.get().getCourses().contains(course.get());
//    }

    private void save(int studentId, int courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        student.orElseThrow(() -> new IllegalArgumentException("Student with given id wasn't found"));
        Optional<Course> course = courseRepository.findById(courseId);
        course.orElseThrow(() -> new IllegalArgumentException("Course with given id wasn't found"));

        student.get().getCourses().add(course.get());
        studentRepository.save(student.get());
    }

    private void delete(int studentId, int courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        student.orElseThrow(() -> new IllegalArgumentException("Student with given id wasn't found"));
        Optional<Course> course = courseRepository.findById(courseId);
        course.orElseThrow(() -> new IllegalArgumentException("Course with given id wasn't found"));

        student.get().getCourses().remove(course.get());
        studentRepository.save(student.get());
    }
}
