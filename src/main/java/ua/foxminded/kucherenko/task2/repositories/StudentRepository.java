package ua.foxminded.kucherenko.task2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.kucherenko.task2.models.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("""
            SELECT NEW Student(s.studentId, s.groupId, s.firstName, s.lastName)
            FROM Student s
            INNER JOIN s.courses c
            WHERE c.courseName = :courseName
            """)
    List<Student> getByCourse(String courseName);

    @Query("SELECT s.studentId FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName")
    List<Integer> getIdByName(String firstName, String lastName);

    @Query("SELECT s.studentId FROM Student s")
    List<Integer> getAllStudentIds();
}
