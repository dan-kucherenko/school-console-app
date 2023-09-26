DELETE FROM StudentCourse sc
WHERE sc.studentId = :studentId AND sc.courseId = :courseId