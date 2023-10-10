package ua.foxminded.kucherenko.task2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.kucherenko.task2.models.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT c.courseId FROM Course c")
    List<Integer> getAllCourseIds();
}
