SELECT students.group_id, group_name, COUNT(*) AS num_of_students
FROM school.students
         INNER JOIN school.groups ON school.students.group_id = school.groups.group_id
WHERE students.group_id IS NOT NULL
GROUP BY students.group_id, group_name
HAVING COUNT(*) <= ?;