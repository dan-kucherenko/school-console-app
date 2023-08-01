DROP TABLE IF EXISTS school.groups;
CREATE TABlE school.groups
(
    group_id   SERIAL PRIMARY KEY,
    group_name VARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS school.courses;
CREATE TABLE school.courses
(
    course_id          SERIAL PRIMARY KEY,
    course_name        VARCHAR(50)  NOT NULL,
    course_description VARCHAR(200) NOT NULL
);

DROP TABLE IF EXISTS school.students;
CREATE TABLE school.students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INT,
    first_name VARCHAR(25) NOT NULL,
    last_name  VARCHAR(25) NOT NULL
);

