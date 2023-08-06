package ua.foxminded.kucherenko.task2.generators;

import java.sql.*;
import java.util.*;

public class StudentCourseGenerator implements IGenerator {
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final String CREATE_STUDENT_COURSE = """
            DROP TABLE IF EXISTS school.student_courses;
            CREATE TABLE IF NOT EXISTS school.student_courses (
            student_id INT REFERENCES school.students(student_id) ON DELETE CASCADE,
            course_id INT REFERENCES school.courses(course_id),
            PRIMARY KEY (student_id, course_id));
            """;

    private static final String INSERT_STUDENT_COURSE = "INSERT INTO school.student_courses (student_id, course_id) VALUES (?, ?)";


    public void createStudentCoursesTable() {
        Connection connection = null;
        PreparedStatement statement = null;


        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            statement = connection.prepareStatement(CREATE_STUDENT_COURSE);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addToDb() {
        createStudentCoursesTable();
        Connection connection = null;
        PreparedStatement studentCourseStatement = null;
        Random random = new Random();

        try {
            connection = DriverManager.getConnection(URL, PROPERTIES);
            studentCourseStatement = connection.prepareStatement(INSERT_STUDENT_COURSE);
            Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();

            for (int studentId = 1; studentId <= NUMBER_OF_STUDENTS; studentId++) {
                Set<Integer> studentCourses = studentCourseMap.computeIfAbsent(studentId, k -> new HashSet<>());
                int numberOfCourses = random.nextInt(3) + 1;
                numberOfCourses = Math.min(numberOfCourses, 10 - studentCourses.size());

                while (studentCourses.size() < numberOfCourses) {
                    int courseId = random.nextInt(10) + 1;
                    studentCourses.add(courseId);
                }
                studentCourseMap.put(studentId, studentCourses);
                for (int courseId : studentCourses) {
                    studentCourseStatement.setInt(1, studentId);
                    studentCourseStatement.setInt(2, courseId);
                    studentCourseStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (studentCourseStatement != null) {
                    studentCourseStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
