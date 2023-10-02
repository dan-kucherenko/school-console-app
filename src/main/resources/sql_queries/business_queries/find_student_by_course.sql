SELECT NEW Student(s.studentId, s.groupId, s.firstName, s.lastName)
FROM Student s
INNER JOIN s.courses c
WHERE c.courseName = :courseName