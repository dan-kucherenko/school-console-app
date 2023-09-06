package ua.foxminded.kucherenko.task2.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.foxminded.kucherenko.task2.models.Student;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Sql({"/database/create_tables.sql", "/database/clear_tables.sql"})
class StudentDaoTest {
    @Autowired
    private StudentDao studentDao;

    @Test
    @Sql("/sample_data/students_samples.sql")
    void get() {
        final int studentId = 32;
        Optional<Student> student = studentDao.get(studentId);
        Assertions.assertFalse(student.isEmpty());
    }

    @Test
    @Sql("/sample_data/students_samples.sql")
    void getAll() {
        final int studentsListSize = 15;
        List<Student> allStudents = studentDao.getAll();
        Assertions.assertEquals(studentsListSize, allStudents.size());
    }


    @Test
    @Sql({"/database/clear_tables.sql", "/sample_data/students_samples.sql"})
    void getAllStudentIds() {
        final int studentsIdListSize = 15;
        List<Integer> allStudentIds = studentDao.getAllStudentIds();
        Assertions.assertEquals(studentsIdListSize, allStudentIds.size());
    }

    @Test
    void save() {
        final Student student = new Student(4, "Daniil", "Kucherenko");
        Assertions.assertNull(studentDao.getIdByName("Daniil", "Kucherenko"));
        studentDao.save(student);
        Assertions.assertNotNull(studentDao.getIdByName("Daniil", "Kucherenko"));
    }

    @Test
    void update() {
        final Student student = new Student(4, "Daniil", "Kucherenko");
        studentDao.save(student);
        final Integer studentId = studentDao.getIdByName("Daniil", "Kucherenko");
        Assertions.assertNotNull(studentId);
        final String changedStudentName = "Daniil1";
        final Student changedStudent = new Student(4, changedStudentName, "Kucherenko");
        studentDao.update(studentId, changedStudent);
        Assertions.assertNotNull(studentDao.getIdByName(changedStudentName, "Kucherenko"));
    }

    @Test
    void delete() {
        final Student student = new Student(4, "Daniil", "Kucherenko");
        studentDao.save(student);
        final Integer studentId = studentDao.getIdByName("Daniil", "Kucherenko");
        Assertions.assertNotNull(studentId);
        studentDao.delete(studentId);
        Assertions.assertNull(studentDao.getIdByName("Daniil", "Kucherenko"));
    }
}
