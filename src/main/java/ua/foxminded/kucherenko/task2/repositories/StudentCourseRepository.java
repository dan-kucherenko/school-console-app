package ua.foxminded.kucherenko.task2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.foxminded.kucherenko.task2.models.Student;

public interface StudentCourseRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT COUNT(c) FROM Student s JOIN s.courses c WHERE s.studentId = :studentId")
    int count(int studentId);
}
