UPDATE Course c
SET c.courseName = :courseName, c.courseDescription = :courseDescription
WHERE c.courseId = :courseId