package ua.foxminded.kucherenko.task2.queries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.dao.StudentCourseDao;
import ua.foxminded.kucherenko.task2.dao.StudentDao;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudent;
import ua.foxminded.kucherenko.task2.queries.add_student.AddStudentData;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourse;
import ua.foxminded.kucherenko.task2.queries.add_student_to_course.AddStudentToCourseData;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class AddStudentToCourseTest {
    @Autowired
    private AddStudentToCourse addStudentToCourse;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private StudentCourseDao studentCourseDao;
    @Autowired
    private AddStudent addStudent;

    @Sql("/sample_data/courses_samples.sql")
    @Test
    void addStudentToCourse_ShouldntThrowException() {
        final int groupId = 4;
        final String firstName = "Daniill";
        final String lastName = "Kucherenko";
        AddStudentData studentData = new AddStudentData(groupId, firstName, lastName);
        addStudent.executeQuery(studentData);

        final int studentId = studentDao.getIdByName(firstName, lastName);
        final int courseId = 6;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);

        Assertions.assertDoesNotThrow(() -> addStudentToCourse.executeQuery(data));
        Assertions.assertTrue(() -> studentCourseDao.exists(studentId, courseId));
    }

    @Test
    void addStudentToCourse_MissingStudent_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final Integer studentId = studentDao.getIdByName(firstName, lastName);
        final int courseId = 6;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);
        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            if (studentId == null) {
                return false;
            }
            return studentCourseDao.exists(studentId, courseId);
        });
    }

    @Test
    void addStudentToCourse_MissingCourseId_ThrowsException() {
        final String firstName = "Michael1";
        final String lastName = "Michaelson1";
        final int courseId = 60;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            final Integer studentId = studentDao.getIdByName(firstName, lastName);
            if (studentId == null) {
                return false;
            }
            return studentCourseDao.exists(studentId, courseId);
        });
    }

    @Test
    void addStudentToCourse_NegativeInput_ThrowsException() {
        final String firstName = "Royal";
        final String lastName = "Marines";
        final int courseId = -6;
        AddStudentToCourseData data = new AddStudentToCourseData(firstName, lastName, courseId);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addStudentToCourse.executeQuery(data));
        Assertions.assertFalse(() -> {
            final Integer studentId = studentDao.getIdByName(firstName, lastName);
            if(studentId == null){
                return false;
            }
            return studentCourseDao.exists(studentId, courseId);
        });
    }
}
