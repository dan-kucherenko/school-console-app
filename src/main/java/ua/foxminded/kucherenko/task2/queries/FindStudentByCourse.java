package ua.foxminded.kucherenko.task2.queries;

import ua.foxminded.kucherenko.task2.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FindStudentByCourse implements IResultingQuery<List<Student>> {
    private String courseName;
    private static final String FIND_STUDENT_BY_COURSE = """
            SELECT students.student_id, group_id, first_name, last_name
            FROM school.student_courses
                     INNER JOIN school.students ON student_courses.student_id = students.student_id
                     INNER JOIN school.courses ON student_courses.course_id = courses.course_id
            WHERE courses.course_name = ?;
            """;

    private void passData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the course for students you want to find: ");
        courseName = sc.next();
        if (courseName.isBlank()) {
            throw new IllegalArgumentException("Course name cant be null");
        }
    }

    @Override
    public List<Student> executeQueryWithRes() {
        List<Student> res = null;
        ResultSet resultSet = null;

        passData();
        try (Connection connection = DriverManager.getConnection(URL, PROPERTIES);
             PreparedStatement statement = connection.prepareStatement(FIND_STUDENT_BY_COURSE)) {
            statement.setString(1, courseName);
            resultSet = statement.executeQuery();
            res = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Student> parseResultSet(ResultSet resultSet) {
        List<Student> res = new ArrayList<>();
        Student student = null;
        try {
            while (resultSet.next()) {
                int studentId = resultSet.getInt("student_id");
                int groupId = resultSet.getInt("group_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                if (groupId == 0) {
                    student = new Student(studentId, firstName, lastName);
                } else {
                    student = new Student(studentId, groupId, firstName, lastName);
                }
                res.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
