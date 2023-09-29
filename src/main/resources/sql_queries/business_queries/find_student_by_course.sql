SELECT NEW Student(s.studentId, s.groupId, s.firstName, s.lastName)
FROM StudentCourse sc
         INNER JOIN Student s ON sc.studentId = s.studentId
         INNER JOIN Course c ON sc.courseId = c.courseId
WHERE c.courseName = :courseName