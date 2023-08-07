DROP TABLE IF EXISTS school.student_courses;
CREATE TABLE IF NOT EXISTS school.student_courses
(
    student_id INT REFERENCES school.students (student_id) ON DELETE CASCADE,
    course_id  INT REFERENCES school.courses (course_id),
    PRIMARY KEY (student_id, course_id)
);