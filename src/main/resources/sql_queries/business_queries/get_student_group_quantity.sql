SELECT COUNT (*) AS num_of_students
FROM Student s
WHERE s.groupId = :groupId