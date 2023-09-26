UPDATE Student s
SET s.groupId = :groupId, s.firstName = :firstName, s.lastName = :lastName
WHERE s.studentId = :studentId