SELECT NEW ua.foxminded.kucherenko.task2.models.GroupStudentsInfo (g.groupId, g.groupName, COUNT(s))
FROM Student s
         INNER JOIN Group g ON s.groupId = g.groupId
WHERE s.groupId IS NOT NULL
GROUP BY g.groupId, g.groupName
HAVING COUNT(g) <= :studentsNum