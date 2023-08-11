SELECT students.student_id, group_id, first_name, last_name
FROM school.student_courses
         INNER JOIN school.students ON student_courses.student_id = students.student_id
         INNER JOIN school.courses ON student_courses.course_id = courses.course_id
WHERE courses.course_name = ?;