package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    @Sql("/sample_data/students_samples.sql")
    void getStudentById() {
        final int studentId = 1;
        final String firstName = "John";
        final String lastName = "Johnson";
        Optional<Student> student = studentDao.get(studentId);
        Assertions.assertFalse(student.isEmpty());
        Assertions.assertEquals(student.get().getFirstName(), firstName);
        Assertions.assertEquals(student.get().getLastName(), lastName);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/students_samples.sql"})
    void getAllStudents() {
        final int studentsListSize = 15;
        final List<Student> expectedStudentsList = List.of(
                new Student(4, "John", "Johnson"),
                new Student(4, "Michael", "Michaelson"),
                new Student(4, "Emma", "Emmson"),
                new Student(4, "Alice", "Alison"),
                new Student(5, "David", "Davidson"),
                new Student(5, "1John1", "Johnson1"),
                new Student(5, "1Michael1", "Michaelson1"),
                new Student(5, "1Emma1", "Emmson1"),
                new Student(6, "1Alice1", "Alison1"),
                new Student(6, "1David1", "Davidson1"),
                new Student(5, "2John2", "Johnson2"),
                new Student(5, "2Michael2", "Michaelson2"),
                new Student(5, "2Emma2", "Emmson2"),
                new Student(6, "2Alice2", "Alison2"),
                new Student(6, "2David2", "Davidson2")
        );
        List<Student> allStudents = studentDao.getAll();
        Assertions.assertEquals(studentsListSize, allStudents.size());
        Assertions.assertEquals(expectedStudentsList, allStudents);
    }


    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/students_samples.sql"})
    void getAllStudentIds() {
        final int studentsIdListSize = 15;
        final Integer[] expectedStudentIds = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        List<Integer> allStudentIds = studentDao.getAllStudentIds();
        Assertions.assertEquals(studentsIdListSize, allStudentIds.size());
        Assertions.assertEquals(Arrays.stream(expectedStudentIds).toList(), allStudentIds);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql"})
    void saveStudent() {
        final int groupId = 4;
        final String firstName = "Daniil";
        final String lastName = "Kucherenko";
        final Student student = new Student(groupId, firstName, lastName);
        Integer studentId;
        studentId = studentDao.getIdByName(firstName, lastName);
        Assertions.assertNull(studentId);
        studentDao.save(student);

        studentId = studentDao.getIdByName(firstName, lastName);
        final Student savedStudent = studentDao.get(studentId).get();
        Assertions.assertNotNull(studentId);

        Assertions.assertEquals(savedStudent.getGroupId(), groupId);
        Assertions.assertEquals(savedStudent.getFirstName(), firstName);
        Assertions.assertEquals(savedStudent.getLastName(), lastName);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/insert_student.sql"})
    void updateStudentById() {
        final int groupId = 4;
        final String initialFirstName = "John";
        final String initialLastName = "Johnson";
        final Integer studentId = studentDao.getIdByName(initialFirstName, initialLastName);
        Assertions.assertNotNull(studentId);
        final String changedStudentName = "Daniil1";
        final Student changedStudent = new Student(groupId, changedStudentName, initialLastName);
        studentDao.update(studentId, changedStudent);

        final Integer changedStudentId = studentDao.getIdByName(changedStudentName, initialLastName);
        final Student updatedStudent = studentDao.get(studentId).get();

        Assertions.assertNotNull(changedStudentId);
        Assertions.assertEquals(updatedStudent.getGroupId(), groupId);
        Assertions.assertEquals(updatedStudent.getFirstName(), changedStudentName);
        Assertions.assertEquals(updatedStudent.getLastName(), initialLastName);
    }

    @Test
    @Sql({"/database/drop_tables.sql", "/database/create_tables.sql", "/sample_data/insert_student.sql"})
    void deleteStudentById() {
        final Integer studentId = studentDao.getIdByName("John", "Johnson");
        Assertions.assertNotNull(studentId);
        studentDao.delete(studentId);
        Assertions.assertNull(studentDao.getIdByName("John", "Johnson"));
    }
}
