SELECT COUNT(s)
FROM Student s
         INNER JOIN s.courses c
WHERE s.id = :studentId
  AND c.id = :courseId