package ua.foxminded.kucherenko.task2.generators;

import ua.foxminded.kucherenko.task2.db.DatabaseConfig;
import ua.foxminded.kucherenko.task2.parser.QueryParser;

import java.sql.*;
import java.util.*;

public class StudentCourseGenerator implements IGenerator {
    private static final int STUDENT_ID_INDEX = 1;
    private static final int COURSE_ID_INDEX = 2;
    private static final int MAX_STUDENT_COURSES_NUM = 3;
    private static final int MAX_COURSE_ID = 10;
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final String CREATE_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/generators/create_student_course.sql";
    private static final String CREATE_STUDENT_COURSE_QUERY= QueryParser.parseQuery(CREATE_STUDENT_COURSE_FILEPATH);

    private static final String INSERT_STUDENT_COURSE_FILEPATH = "src/main/resources/sql_queries/generators/insert_student_course.sql";
    private static final String INSERT_STUDENT_COURSE = QueryParser.parseQuery(INSERT_STUDENT_COURSE_FILEPATH);


    public void createStudentCoursesTable() {
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(CREATE_STUDENT_COURSE_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToDb() {
        createStudentCoursesTable();
        Random random = new Random();
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getUrl(), DatabaseConfig.getProps());
             PreparedStatement statement = connection.prepareStatement(INSERT_STUDENT_COURSE)) {
            Map<Integer, Set<Integer>> studentCourseMap = new HashMap<>();
            for (int studentId = 1; studentId <= NUMBER_OF_STUDENTS; studentId++) {
                Set<Integer> studentCourses = studentCourseMap.computeIfAbsent(studentId, k -> new HashSet<>());
                int numberOfCourses = random.nextInt(MAX_STUDENT_COURSES_NUM) + 1;
                numberOfCourses = Math.min(numberOfCourses, MAX_COURSE_ID - studentCourses.size());

                while (studentCourses.size() < numberOfCourses) {
                    int courseId = random.nextInt(MAX_COURSE_ID) + 1;
                    studentCourses.add(courseId);
                }
                studentCourseMap.put(studentId, studentCourses);
                for (int courseId : studentCourses) {
                    statement.setInt(STUDENT_ID_INDEX, studentId);
                    statement.setInt(COURSE_ID_INDEX, courseId);
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
