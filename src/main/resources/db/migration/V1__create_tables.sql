CREATE SCHEMA IF NOT EXISTS school;
CREATE TABlE IF NOT EXISTS school.groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS school.courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(50)  NOT NULL,
    course_description VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS school.students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT,
    first_name VARCHAR(25) NOT NULL,
    last_name  VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS school.student_courses
(
    student_id INT REFERENCES school.students (student_id) ON DELETE CASCADE,
    course_id  INT REFERENCES school.courses (course_id),
    PRIMARY KEY (student_id, course_id)
);